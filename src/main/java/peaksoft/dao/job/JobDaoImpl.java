package peaksoft.dao.job;

import peaksoft.config.Util;
import peaksoft.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao{
    private Connection connection = Util.getConnection();
    @Override
    public void createJobTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
                    create table jobs(
                    id serial primary key ,
                    position varchar,
                    profession varchar,
                    description varchar,
                    experience int);""");
            System.out.println("<<<<<< Successfully created table Jobs! >>>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String sqlQuery = """
                insert into jobs(position,profession,description,experience)
                values (?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.execute();
            System.out.println("<<<<<< Successfully added new Job! >>>>>>");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        String sqlQuery = """
                select * from jobs where id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1,jobId);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            Job job = new Job();
            if (!resultSet.next()) {
                System.out.println("Does not exist!");
            }
            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
            }
            resultSet.close();
            return job;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {

        List<Job> sorted = new ArrayList<>();
        if (ascOrDesc.toLowerCase().equals("asc")){
            String query = """
                    Select * from jobs order by experience;
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    Job job = new Job();
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString(2));
                    job.setProfession(resultSet.getString(3));
                    job.setDescription(resultSet.getString(4));
                    job.setExperience(resultSet.getInt(5));
                    sorted.add(job);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }if (ascOrDesc.toLowerCase().equals("desc")){
            String query = """
                    Select * from students order by age desc;
                    """;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    Job job = new Job();
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString(2));
                    job.setProfession(resultSet.getString(3));
                    job.setDescription(resultSet.getString(4));
                    job.setExperience(resultSet.getInt(5));
                    sorted.add(job);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return sorted;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String sqlQuery = """
                select * from jobs where id = (select id from employees where id=jobs(id));
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1,employeeId);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            Job job = new Job();
            if (!resultSet.next()) {
                System.out.println("Does not exist!");
            }
            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
            }
            resultSet.close();
            return job;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDescriptionColumn() {
        String query = """
                alter table jobs drop column description;
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Successfully deleted column Description to Jobs!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
