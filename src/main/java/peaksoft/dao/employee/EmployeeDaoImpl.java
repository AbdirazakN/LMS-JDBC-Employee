package peaksoft.dao.employee;

import peaksoft.config.Util;
import peaksoft.models.Employee;
import peaksoft.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private Connection connection;

    public EmployeeDaoImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createEmployee() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    create table employees(
                    id serial primary key ,
                    first_name varchar,
                    last_name varchar,
                    age int,
                    email varchar unique ,
                    job_id int references jobs(id));""");
            System.out.println("<<<<<< Successfully created table Employees! >>>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sqlQuery = """
                insert into employees(first_name,last_name,age,email,job_id)
                values (?,?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.execute();
            System.out.println("<<<<<< Successfully added new Employee! >>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    drop table employees;
                    """);
            System.out.println("<<<<<< Successfully deleted Table >>>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    truncate employees;
                    """);
            System.out.println("<<<<<< Successfully cleaned Table >>>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String query = """
                update employees 
                set first_name = ? ,
                last_name = ?,
                age = ?,
                email = ?,
                job_id = ?
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3,employee.getAge());
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setInt(5,employee.getJobId());
            preparedStatement.setLong(6,id);

            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("Successfully updated!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        String query = """
                select * from employees;
                """;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
                allEmployees.add(employee);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return allEmployees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sqlQuery = """
                select * from employees where email = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1,email);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            Employee employee = new Employee();
            if (!resultSet.next()) {
                System.out.println("Does not exist!");
            }
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
            }
            resultSet.close();
            return employee;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee,Job> result = new HashMap<>();
        String query = """
                select * from employees full join jobs j on employees.job_id = j.id where employees.id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee();
                Job job = new Job();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
                job.setId(resultSet.getLong(7));
                job.setPosition(resultSet.getString(8));
                job.setProfession(resultSet.getString(9));
                job.setExperience(resultSet.getInt(10));
                result.put(employee, job);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> allEmployees = new ArrayList<>();
        String query = """
                select * from employees where id in(select id from job where position=?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,position);
            preparedStatement.executeQuery();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setJobId(resultSet.getInt(5));
                allEmployees.add(employee);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return allEmployees;
    }
}
