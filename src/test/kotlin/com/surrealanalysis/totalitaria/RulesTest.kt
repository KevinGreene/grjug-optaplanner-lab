package com.surrealanalysis.totalitaria

import com.surrealanalysis.totalitaria.domain.*
import org.junit.Test
import org.optaplanner.core.api.solver.SolverFactory
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier

class RulesTest {
    var id = 0
    fun id(): Int {
        id += 1
        return id
    }

    val scoreVerifier: HardSoftScoreVerifier<Schedule> = HardSoftScoreVerifier(SolverFactory.createFromXmlResource("com/surrealanalysis/totalitaria/solver/solverConfig.xml"))

    @Test
    fun fullyAllocated() {
        val project1 = Project(id(), "Project 1")
        val task1 = ProjectTask(id(), project1, 1, "Task 1")
        val project2 = Project(id(), "Project 2")
        val task2 = ProjectTask(id(), project2, 1, "Task 2")
        val employee = Employee(id(), "Employee")
        val day = Day(id())
        val timeGrain = TimeGrain(id(), day)
        val employeeTaskAssignment1 = EmployeeShiftTaskAssignment(id(), employee, timeGrain, task1)
        val schedule = Schedule(projects = listOf(project1, project2),
                employees = listOf(employee),
                skills = listOf<Skill>(),
                days = listOf(day),
                timeGrains = listOf(timeGrain),
                projectTasks = listOf(task1, task2),
                assignments = listOf(employeeTaskAssignment1))

        scoreVerifier.assertHardWeight("Project tasks should be fully allocated", -1, schedule)
    }
}