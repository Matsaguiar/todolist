package br.com.matsaguiar.todolist.task;

import java.util.UUID;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
		try {
			return new ResponseEntity<TaskModel>(taskService.createTask(taskModel, request), HttpStatus.OK);
		} catch (DataException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro ao criar task!", HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/listByUser")
    public ResponseEntity<?> listByUser(HttpServletRequest request){
		try {
			return new ResponseEntity<>(taskService.listByUser((UUID) request.getAttribute("idUser")), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro ao listar tasks!", HttpStatus.CONFLICT);
		} 
    }
	
	@PutMapping("/update/{idTask}")
	public ResponseEntity<?> update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID idTask){
		try {
			return new ResponseEntity<>(taskService.updateTask((UUID) request.getAttribute("idUser"), taskModel, idTask), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro ao listar tasks!", HttpStatus.CONFLICT);
		} 
	}

}
