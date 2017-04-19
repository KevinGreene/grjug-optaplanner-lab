package com.surrealanalysis.totalitaria

import com.surrealanalysis.totalitaria.domain.Schedule
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.File

@SpringBootApplication
class TotalitariaApplication

fun main(args: Array<String>) {
    SpringApplication.run(TotalitariaApplication::class.java, *args)
    val scheduleGenerator: ScheduleGenerator = ScheduleGenerator(
            employeeCount = 10,
            projectCount = 5,
            skillCount = 6,
            tasksPerProject = 20,
            timeGrainInMinutes = 30,
            minutesInDay = 480,
            dayCount = 10)

    val schedule: Schedule = scheduleGenerator.generate()

    val solverFactory: SolverFactory<Schedule> = SolverFactory.createFromXmlResource(
            "com/surrealanalysis/totalitaria/solver/solverConfig.xml")
    val solver: Solver<Schedule> = solverFactory.buildSolver()
    val newSchedule = solver.solve(schedule)

    val scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector()
    scoreDirector.setWorkingSolution(newSchedule)

    println("Constraint Matches")

    scoreDirector.constraintMatchTotals.forEach {
        println(it.constraintName)
        println(it.scoreTotal.toShortString())
        println()
    }

    val rows = newSchedule.employees.map { employee ->
        val employeeAssignments = newSchedule.assignments.filter { assignment -> assignment.employee == employee }
        val taskNames = employeeAssignments.sortedBy { it.timeGrain.index }.map { "${it.task?.project?.name}: ${it.task?.name}" }
        listOf(employee.name) + taskNames
    }

    val rowLength = rows.first().size
    val formatString = "%-15s,  ".repeat(rowLength) + "\n"
    print(formatString)
    File("schedule.csv").printWriter().use { out ->
        rows.forEach { row ->
            println(formatString.format(*row.toTypedArray()))
            out.println(formatString.format(*row.toTypedArray()))
        }
    }
}
