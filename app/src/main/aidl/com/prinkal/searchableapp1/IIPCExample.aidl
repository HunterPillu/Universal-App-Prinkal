// IIPCExample.aidl
package com.prinkal.searchableapp1;
import com.prinkal.searchableapp.data.model.SampleData;

// Declare any non-default types here with import statements

interface IIPCExample {
    /** Request the process ID of this service */
    int getPid();

    /** Count of received connection requests from clients */
    int getConnectionCount();

    /** Set displayed value of screen */
    void setDisplayedValue(String packageName, int pid, String data);


    List<SampleData> getSearchedResult(String packageName, int pid, String data);
}