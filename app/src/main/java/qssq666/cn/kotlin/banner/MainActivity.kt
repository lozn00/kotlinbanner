package qssq666.cn.kotlin.banner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

//import activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var tv = findViewById(R.id.tv_test) as TextView
//        find<TextView>(R.id.tv_test).setText("")
        var text1=findViewById(R.id.tv_test)
//        tv.setText("hello");
      /*  verticalLayout {

        }*/
//        textview.setText()
        tv_test.setText("hello")

    }
}
