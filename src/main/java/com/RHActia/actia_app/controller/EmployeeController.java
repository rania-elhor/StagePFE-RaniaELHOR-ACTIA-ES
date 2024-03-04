package com.RHActia.actia_app.controller;

import com.RHActia.actia_app.model.Employee;
import com.RHActia.actia_app.model.Gender;
import com.RHActia.actia_app.model.ImageModel;
import com.RHActia.actia_app.services.EmployeeService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/emp")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService ;

    // add new employee
    @PostMapping("/addEmployee")
    public Employee addEmployee(@RequestPart("employee") Employee employee,
                                @RequestPart("imagePath") MultipartFile  [] files) {
        try {
            Set<ImageModel> images = uploadImage(files); // Convertir le tableau en liste
            employee.setEmployeeImages(images);

            System.out.println(files.length);
            return employeeService.addEmployee(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @GetMapping("/AllEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    // add more than 1 employee
    @PostMapping("/addEmployees")
    public List<Employee> addAllEmployee (@RequestBody List<Employee> employees){

        return employeeService.addAllEmployees(employees);
    }

    //GEt employee by Id
    @GetMapping("/getEmployeeByID/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return employeeService.getEmployeeByID(id);
    }
    // Get employee by name
    @GetMapping("/getEmployeeByName/{firstname}")
    public Employee getEmployeeByName(@PathVariable String firstname ){
        return  employeeService.getEmployeeByName(firstname);
    }
    @GetMapping("/searchByGender/{gender}")
    public List<Employee> searchByGender(@PathVariable Gender gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    /*@GetMapping("/searchByBirthDateRange/{startDate}/{endDate}")
    public List<Employee> searchByBirthDateRange(
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date  startDate,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date  endDate) {

        return employeeService.getEmployeesByBirthDateRange(startDate, endDate);
    }
    @GetMapping("/searchByBirthYear/{year}")
    public List<Employee> searchByBirthYear(@PathVariable int year) {
        return employeeService.getEmployeesByBirthYear(year);
    }*/
    //Update Employee
    //Update Employee
    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable int id,
                                   @RequestPart("employee") Employee employee,
                                   @RequestPart("imagePath") MultipartFile[] files) {
        try {
            Set<ImageModel> images = uploadImage(files); // Convertir le tableau en liste
            employee.setEmployeeImages(images);
            employee.setId(id);
            return employeeService.updateEmployee(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<ImageModel>();

        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            imageModels.add(imageModel);
        }
        return imageModels;
    }
    //Delete employee
    @DeleteMapping("/deleteEmployeeById/{id}")
    public boolean deleteEmployeeByID(@PathVariable int id){
        return employeeService.deleteEmployeeByID(id);
    }
    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile  file) {
        try {
            String imageUrl = employeeService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}
