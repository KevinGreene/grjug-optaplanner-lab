package com.surrealanalysis.totalitaria.domain

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore

@PlanningSolution
class Schedule() {
    @ProblemFactCollectionProperty
    lateinit var projects: List<Project>

    @ProblemFactCollectionProperty
    lateinit var employees: List<Employee>

    @ProblemFactCollectionProperty
    lateinit var skills: List<Skill>

    @ProblemFactCollectionProperty
    lateinit var days: List<Day>

    @ProblemFactCollectionProperty
    lateinit var timeGrains: List<TimeGrain>

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "taskRange")
    lateinit var projectTasks: List<ProjectTask>

    @PlanningEntityCollectionProperty
    lateinit var assignments: List<EmployeeShiftTaskAssignment>

    @PlanningScore
    lateinit var score: HardSoftScore

    constructor(projects: List<Project>,
                employees: List<Employee>,
                skills: List<Skill>,
                days: List<Day>,
                timeGrains: List<TimeGrain>,
                projectTasks: List<ProjectTask>,
                assignments: List<EmployeeShiftTaskAssignment>) : this() {
        this.projects = projects
        this.employees = employees
        this.skills = skills
        this.days = days
        this.timeGrains = timeGrains
        this.projectTasks = projectTasks
        this.assignments = assignments
    }

}