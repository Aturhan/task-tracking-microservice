package com.abdullahturhan.service;

import com.abdullahturhan.dto.*;
import com.abdullahturhan.exception.InvalidTaskDateException;
import com.abdullahturhan.exception.TaskNotFoundException;
import com.abdullahturhan.exception.UserNotFoundOrUserRoleIsNotValidException;
import com.abdullahturhan.model.Status;
import com.abdullahturhan.model.Task;
import com.abdullahturhan.model.User;
import com.abdullahturhan.producer.KafkaProducer;
import com.abdullahturhan.repository.TaskManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TaskManagementService {
    private final TaskManagementRepository managementRepository;
    private final WebClient.Builder webClient;

    private final KafkaProducer producer;

    public TaskManagementService(TaskManagementRepository managementRepository, WebClient.Builder webClient, KafkaProducer producer) {
        this.managementRepository = managementRepository;
        this.webClient = webClient;
        this.producer = producer;
    }

    public List<SearchTaskResponse> getTaskByLabel(String label) throws TaskNotFoundException {
       final List<Task> task = managementRepository.findTaskByLabel(label)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by label = "+ label));
       return SearchTaskResponse.converter(task);
    }

    public String createAndAssignTask(CreateTaskRequest request) throws UserNotFoundOrUserRoleIsNotValidException {
            isDateValid(request.startDate(),request.finishDate());
            final User user = fetchUserFromUserService(request.email());
            log.info("fetched user from user-service= " + user);
            final Task task = Task.builder()
                    .title(request.title())
                    .label(request.label())
                    .description(request.description())
                    .status(Status.GET_PROCESSING)
                    .startDate(request.startDate())
                    .finishDate(request.finishDate())
                    .user(user)
                    .build();
            Task savedTask = managementRepository.save(task);
            log.info(String.format("Saved task= %s" ,task));

         // TODO convert task to payload for kafka
        CreateTaskPayload payload = CreateTaskPayload.converter(task);
            producer.publish("create_assign_task",payload);
            log.info(String.format("Create & assign task event sent to notification-service = %s",payload));
            return savedTask.getId();
    }

    private User fetchUserFromUserService(String email) throws UserNotFoundOrUserRoleIsNotValidException {
        WebClient client = WebClient.create("http://localhost:8081/api/users");
        SearchUserResponse response = client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/email")
                        .queryParam("email", email).build())
                .retrieve()
                .bodyToMono(SearchUserResponse.class)
                .block();

        if (response != null){
            return User.builder()
                    .userId(response.id())
                    .firstName(response.firstName())
                    .lastName(response.lastName())
                    .email(response.email())
                    .build();
        }
        throw new UserNotFoundOrUserRoleIsNotValidException("User is not found or its role is not user please check it");
    }

    private void isDateValid(LocalDate start, LocalDate finish){
        if(finish.isBefore(start)){
            throw new InvalidTaskDateException("Task's start date should be before");
        }
    }

    public void updateTaskToCompleted(String id) throws TaskNotFoundException {
       final Task task = managementRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by id = "+ id));
       log.info(String.format("Task updated to completed = %s",task));
       task.setStatus(Status.COMPLETED);
       final Task savedTask = managementRepository.save(task);


       LocalDate date = LocalDate.now();

       String feedback;
       if (savedTask.getFinishDate().isBefore(date)){
          feedback = "Congrats you finished task before its finish date!";
       }else {
           feedback = "Please pay attention cause of task completion date!";
       }
        TaskCompletedPayload payload = TaskCompletedPayload.payload(task, feedback);
       producer.publish("task_completed", payload);
       log.info(String.format("Task completed event sent to notification-service = %s",payload));
    }

    public void deleteTaskById(String id) throws TaskNotFoundException {
        final Task task = managementRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found by id = "+ id));
        managementRepository.delete(task);
    }

}
