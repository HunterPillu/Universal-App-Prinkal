package com.prinkal.searchableapp.utils

import com.prinkal.searchableapp.data.model.SampleData

object DummyDataGenerator {
    fun getSampleData(): ArrayList<SampleData> {
        val sampleList:ArrayList<SampleData> = arrayListOf()
        sampleList.add(SampleData(1,"test1","test1 description","test1 photourl"))
        sampleList.add(SampleData(2,"test2","test2 description","test2 photourl"))
        sampleList.add(SampleData(3,"test3","test3 description","test3 photourl"))
        return sampleList
    }
}