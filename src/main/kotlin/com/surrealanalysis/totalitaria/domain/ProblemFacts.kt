package com.surrealanalysis.totalitaria.domain

import com.google.common.math.IntMath

abstract class ProblemFact {
    abstract val id: Int
    override fun equals(other: Any?): Boolean {
        return other != null
                && other is ProblemFact
                && other.javaClass === this.javaClass
                && other.id == id
    }

    override fun hashCode(): Int = id
}

data class Project(override val id: Int,
                   val name: String) : ProblemFact()

data class ProjectTask(override val id: Int,
                       val project: Project,
                       val grains: Int,
                       val name: String) : ProblemFact()

data class Employee(override val id: Int,
                    val name: String) : ProblemFact()

data class Skill(override val id: Int,
                 val name: String) : ProblemFact()

data class TimeGrain(val index: Int, val day: Day) {
    override fun equals(other: Any?): Boolean {
        return other != null
                && other is TimeGrain
                && other.index == index
    }

    override fun hashCode(): Int = index
}

data class Day(val index: Int)