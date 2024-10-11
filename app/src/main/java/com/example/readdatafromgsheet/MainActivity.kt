package com.example.readdatafromgsheet

import android.content.ComponentCallbacks
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.readdatafromgsheet.ui.theme.ReadDataFromGSheetTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadDataFromGSheetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var items by remember { mutableStateOf(emptyList<SheetItems>()) }

                   // items = getData(this)
                   getData(this){fetchitems-> items = fetchitems }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items){
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.fillParentMaxWidth().align(Alignment.Center)) {
                                    Text("Name:${it.name} Address:${it.address}")
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}


fun getData(context: Context,callback:(List<SheetItems>)->Unit){
    val jsonurl = "https://script.google.com/macros/s/AKfycbxbeNWK1_xdMP_tORCpjj8_UGclqWKIVBbFx2JTq3z6Dy4GeHcn6fkc_nIThqRqaK53/exec"
    val requestQueue = Volley.newRequestQueue(context)
    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET,jsonurl,null,
    {

        //response lamnda funciton
        var jsonObject:JSONObject
        var name:String
        var address:String
        var items =  mutableListOf<SheetItems>()

        for(i in 0 until it.length()){
            jsonObject = it.getJSONObject(i)
            name = jsonObject.getString("name")
            address = jsonObject.getString("address")

           items.add(SheetItems(name,address))
        }
        callback(items) //returning the list using callback function
    },

    {
        //error listener lambda funciton
    })
    requestQueue.add(jsonArrayRequest)

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadDataFromGSheetTheme {

    }
}