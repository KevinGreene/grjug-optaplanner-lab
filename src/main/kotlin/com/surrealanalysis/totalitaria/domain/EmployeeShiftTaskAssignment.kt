package com.surrealanalysis.totalitaria.domain

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable

@PlanningEntity
class EmployeeShiftTaskAssignment {

    var id: Int = 0

    lateinit var employee: Employee
    lateinit var timeGrain: TimeGrain

    @PlanningVariable(valueRangeProviderRefs = arrayOf("taskRange"), nullable = true)
    var task: ProjectTask? = null

    constructor()

    constructor(id: Int, employee: Employee, timeGrain: TimeGrain) : this() {
        this.id = id
        this.employee = employee
        this.timeGrain = timeGrain
    }

    override fun equals(other: Any?): Boolean {
        return other != null
                && other is EmployeeShiftTaskAssignment
                && other.id == id
    }

    override fun hashCode(): Int = id


}