package br.com.matsaguiar.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.matsaguiar.todolist.user.UserModel;
import br.com.matsaguiar.todolist.user.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserService userService;

	public TaskModel createTask(TaskModel taskModel, HttpServletRequest request) {
		
		LocalDateTime currentDate = LocalDateTime.now();
		
		if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) 
			throw new DataIntegrityViolationException("As datas devem ser maiores que a atual!");
		
		if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) 
			throw new DataIntegrityViolationException("A data de início deve ser anterior a data de término!");
		
		UserModel user =userService.findById((UUID) request.getAttribute("idUser")); 
		
		if (user == null)
			throw new DataException("Usuário não encontrado, não foi possível salvar a tarefa!", null);
		
		taskModel.setIdUser(user.getId());
		return taskRepository.save(taskModel);
	}
	
	public List<TaskModel> listByUser(UUID idUser){
		return taskRepository.findByIdUser(idUser);
	}
	
	public TaskModel updateTask(UUID idUser, TaskModel taskModel, UUID idTask) {
		TaskModel task = taskRepository.findById(idTask).get();
		
		if(task == null)
			throw new DataIntegrityViolationException("Não há task com esse ID!");
		if(!task.getIdUser().equals(idUser))
			throw new DataIntegrityViolationException("Essa tarefa não pertence ao usuário logado!");
		
		task.setDescription(taskModel.getDescription() != null ? taskModel.getDescription() : task.getDescription());
		task.setEndAt(taskModel.getEndAt() != null ? taskModel.getEndAt() : task.getEndAt());
		task.setStartAt(taskModel.getStartAt() != null ? taskModel.getStartAt() : task.getStartAt());
		task.setTitle(taskModel.getTitle() != null ? taskModel.getTitle() : task.getTitle());
		
		return taskRepository.save(task);
	} 
}
