package com.example.hrm_new.controller.employee;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.hrm_new.entity.employee.Transfer;
import com.example.hrm_new.repository.employee.TransferRepository;
import com.example.hrm_new.service.employee.TransferService;






@CrossOrigin
@RestController
public class TransferController {
	@Autowired
    private  TransferService service;
	
	@Autowired
    private  TransferRepository repo;

	@GetMapping("/transfer")
	public ResponseEntity<?> getTransfers() {
		try {
			List<Transfer> Transfers = service.listAll();
			return ResponseEntity.ok(Transfers);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Transfers: " + e.getMessage());
		}
	}


	
	@PostMapping("/transfer/save")
	public ResponseEntity<String> saveTransfer(@RequestBody Transfer Transfer) {
		try {
			Transfer.setStatus(true);
			service.saveOrUpdate(Transfer);
			return ResponseEntity.ok("Transfer saved with id: " + Transfer.getTransferId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Transfer: " + e.getMessage());
		}
	}

	@PutMapping("/transfer/or/{id}")
    public ResponseEntity<Boolean> toggleComplaintsStatus(@PathVariable(name = "id") long id) {
        try {
        	Transfer complaints = service.getById(id);
            if (complaints != null) {
          
                boolean currentStatus = complaints.isStatus();
                complaints.setStatus(currentStatus);
                service.saveOrUpdate(complaints); 
            } else {
                return ResponseEntity.ok(false); 
            }

            return ResponseEntity.ok(complaints.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
 }

	@PutMapping("/transfer/edit/{id}")
	public ResponseEntity<Transfer> updateTransfer(@PathVariable("id") long id, @RequestBody Transfer Transfer) {
		try {
			Transfer existingTransfer = service.getById(id);
			if (existingTransfer == null) {
				return ResponseEntity.notFound().build();
			}
			existingTransfer.setDate(Transfer.getDate());
			existingTransfer.setRoleName(Transfer.getRoleName());
			existingTransfer.setDescription(Transfer.getDescription());
			existingTransfer.setEmployeeId(Transfer.getEmployeeId());
			existingTransfer.setLocation(Transfer.getLocation());
			existingTransfer.setDesignationName(Transfer.getDesignationName());
			existingTransfer.setStatus(Transfer.isStatus());
			
			
			

			service.saveOrUpdate(existingTransfer);
			return ResponseEntity.ok(existingTransfer);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/transfer/delete/{id}")
	public ResponseEntity<String> deleteTransfer(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Transfer deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Transfer: " + e.getMessage());
		}
	}
	
	@GetMapping("/transfer/view")
	public List<Map<String,Object>>GoodAllReansfer(){
		return service.AllTravel();
	}
	
	@GetMapping("/transfer/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.allDetailsOfAnnouncement(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("location", totalList.getValue().get(0).get("location"));
	        announcementMap.put("Announcement Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
	
	/////////////16 /////////////
	
	
	@PostMapping("/transfers/date")
	public List<Map<String, Object>> getEmployeeTransfersByDate(@RequestParam("date") String transferDateStr) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date parsedDate;
	    try {
	        parsedDate = dateFormat.parse(transferDateStr);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	    List<Map<String, Object>> transfers = repo.findAwardsByEmployeeIdAndDate(parsedDate);
	    List<Map<String, Object>> result = new ArrayList<>();
	    result.addAll(transfers);

	    return result;
	}
///////////////17//////////////////////////
	@GetMapping("/transfers/count")
	private List<Map<String, Object>>Allcount(){
		return repo.getEmployeeTransferCountByYear();
	}





}