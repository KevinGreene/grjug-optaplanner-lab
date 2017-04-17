package com.surrealanalysis.totalitaria

import com.surrealanalysis.totalitaria.domain.Schedule
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.api.solver.SolverFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TotalitariaApplication

fun main(args: Array<String>) {
    SpringApplication.run(TotalitariaApplication::class.java, *args)
    val scheduleGenerator: ScheduleGenerator = ScheduleGenerator(
            employeeCount = 10,
            projectCount = 5,
            skillCount = 6,
            tasksPerProject = 20,
            timeGrainInMinutes = 10,
            minutesInDay = 480,
            dayCount = 10)

    val schedule: Schedule = scheduleGenerator.generate()

    val solverFactory: SolverFactory<Schedule> = SolverFactory.createFromXmlResource(
            "com/surrealanalysis/totalitaria/solver/solverConfig.xml")
    val solver: Solver<Schedule> = solverFactory.buildSolver()
    val newSchedule = solver.solve(schedule)

    val scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector()
    scoreDirector.setWorkingSolution(newSchedule)

    print(newSchedule.score)

}
