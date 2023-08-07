package com.example.homework

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private var rootView: FrameLayout? = null

    /** RecycleView 实例 */
    private var recycleView: RecyclerView? = null

    /** RecycleView 的适配器 */
    private var adapter: DemoAdapter? = null

    private lateinit var fab_scroll_to_top: FloatingActionButton
    private lateinit var gestureDetector : GestureDetector
    private var dX: Float = 0f
    private var dY: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(R.id.root)
        recycleView = findViewById(R.id.rv_demo)

        // 初始化 RecyclerView
        adapter = DemoAdapter(ArrayList())
        recycleView?.adapter = adapter
        recycleView?.layoutManager = LinearLayoutManager(this)

        addRecycleView()

        // 设置 BottomNavigationView 的选项卡点击事件
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_option1 -> {
                    // 处理选项1的跳转逻辑
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_option2 -> {
                    // 处理选项2的跳转逻辑
                    val intent = Intent(this, VideoActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_option3 -> {
                    // 处理选项3的跳转逻辑
                    val intent = Intent(this, PersonActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

//        // 找到 FloatingActionButton
//        fab_scroll_to_top = findViewById(R.id.fab_scroll_to_top)
//
//        // 创建 GestureDetector 对象
//            GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
//                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//                    // 处理 FloatingActionButton 的点击事件
//                    onTopClicked(fab_scroll_to_top)
//                    return true
//                }
//            })
//
//        // 添加触摸监听器
//        fab_scroll_to_top.setOnTouchListener { view, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    // 记录用户按下的坐标
//                    dX = view.x - event.rawX
//                    dY = view.y - event.rawY
//                    true
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    // 更新 FloatingActionButton 的位置
//                    view.animate()
//                        .x(event.rawX + dX)
//                        .y(event.rawY + dY)
//                        .setDuration(0)
//                        .start()
//                    true
//                }
//                MotionEvent.ACTION_UP -> {
//                    // 如果 dx 和 dy 都等于 0，则将事件视为点击事件
//                    val epsilon = 1e-1f
//                    if (Math.abs(dX) < epsilon && Math.abs(dY) < epsilon) {
//                        onTopClicked(fab_scroll_to_top)
//                    } else {
//                        // 记录 FloatingActionButton 的最终位置
//                        val preferences = getPreferences(Context.MODE_PRIVATE)
//                        val editor = preferences.edit()
//                        editor.putFloat("fab_x", view.x)
//                        editor.putFloat("fab_y", view.y)
//                        editor.apply()
//                    }
//                    true
//                }
//                else -> gestureDetector.onTouchEvent(event)
//            }
//        }
    }

    private fun reloadData() {
        val sharedPreferences = getSharedPreferences("news_list_test", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("news_list_test", null)

        if (json != null) {
            // 如果 SharedPreferences 中有存储的数据，则直接使用该数据
            val type = object : TypeToken<ArrayList<ItemBean>>() {}.type
            val list = gson.fromJson<ArrayList<ItemBean>>(json, type)
            adapter = DemoAdapter(list)
            recycleView?.adapter = adapter
            recycleView?.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    /**
     * 添加RecycleView
     */
    private fun addRecycleView() {
        // 获取 RecyclerView 控件
        recycleView = findViewById(R.id.rv_demo)

        // 创建适配器并设置到 RecyclerView 中
        adapter = DemoAdapter(createDemoDate())
        recycleView?.adapter = adapter

        // 设置布局管理器
        recycleView?.layoutManager = LinearLayoutManager(this)

        // 将列表存储到 SharedPreferences 中
        val gson = Gson()
        val list = createDemoDate()

// 将列表数据转换为 JSON 字符串
        val newJson = gson.toJson(list)

// 获取 SharedPreferences 实例，并将 JSON 字符串存储到 SharedPreferences 中
        val sharedPreferences = getSharedPreferences("news_list_test", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("news_list_test", newJson)
        editor.apply()
    }

    private fun createDemoDate(): ArrayList<ItemBean> {

        val list = ArrayList<ItemBean>()
        val example1 = "7月25日，工作人员在“中国天眼”总控室内工作。\n" +
                "　　记者7月25日从FAST运行和发展中心获悉，截至目前，被誉为“中国天眼”的500米口径球面射电望远镜（FAST）已发现800余颗新脉冲星。\n" +
                "　　“中国天眼”于2016年9月25日落成启用，2020年1月11日通过国家验收并正式开放运行，是全球最大且最灵敏的单口径射电望远镜。\n" +
                "　　目前，“中国天眼”已进入成果爆发期，今年以来发布多个重要成果，包括发现轨道周期仅为53分钟的脉冲星双星系统、探测到纳赫兹引力波存在的关键性证据等，继续保持了我国在低频射电天文学方面的国际领先地位。新华社记者 欧东衢 摄"
        val example2 = "据重庆市水文监测总站监测，25日8时至26日8时，重庆东南部和西部大到暴雨、局地大暴雨，其余地区小到中雨、局地大到暴雨。巴南、九龙坡、荣昌、江津、南川、巫山、黔江、武隆、酉阳、秀山、彭水等11个区县出现暴雨，其中巴南、黔江、酉阳、秀山等4个区县出现大暴雨。最大日降雨量出现在酉阳县腴地乡下腴村，为135.5毫米。受降雨影响，重庆黔江区阿蓬江、袁溪河、金溪河、马喇河，酉阳县酉阳河，秀山县酉水等6条中小河流超警，其中黔江区袁溪河、马喇河等2条中小河流超保，截至今日8时，均已退至警戒水位以下；其余秀山梅江、涪陵龙潭河、巴南一品河等33条中小河流出现1~10米涨水过程，最高水位均未超过警戒水位。\n" +
                "\n" +
                "四川境内嘉陵江、渠江、涪江、琼江流域小到中雨、局地大雨；贵州境内乌江流域中到大雨、局地暴雨。各江河主要控制站水势基本平稳，无明显涨水过程。\n" +
                "\n" +
                "水利部门预计26日8时～28日8时，重庆西部部分中小河流可能出现不同程度涨水过程，个别河流可能超警。28日8时～29日8时，重庆中小河流水势基本平稳，无明显涨水过程。（总台记者 伍黎明 詹盛）"
        val example3 = "北京时间2023年7月27日4时2分，我国在西昌卫星发射中心使用长征二号丁运载火箭，采取一箭三星方式：成功将遥感三十六号卫星发射升空。"
        val example4 = "27日，我国自主研制的海底地震勘探采集核心装备\"海脉\"在渤海海域正式投入使用。标志着我国在高瑞海洋油气勘探技术上迈出关键一步。"
        val example5 = "央视网消息：截至目前，已有28个省市自治区公布了上半年的经济“成绩单”。数据显示，今年上半年，地区经济总体呈现回升向好态势，新动能加快壮大，高质量发展扎实推进。\n" +
                "\n" +
                "根据已经发布的数据来看，东部地区“压舱石”作用持续发挥。今年上半年，广东、江苏经济总量超过6万亿元；山东超过4万亿元；上海增速最高，达9.7%；海南增速达8.6%。东部地区主要经济指标稳步改善，特别是长三角科创产业融合步伐加快，粤港澳大湾区建设持续深化、海南自贸港加快建设等举措，带动经济较快增长。" +
                "\n" +
                "在中部地区，主要省份二季度以来工业增速逐月回升，呈现稳步恢复态势。上半年，河南经济总量超过3万亿元，湖北、湖南超过2万亿元。在西部地区，能源绿色转型稳步推进，短板领域加快补齐，经济稳中有升。特别是西北省区的固定资产投资、规模以上工业增加值都保持较快增长。上半年，甘肃和青海增速超过6%，四川的增速与全国持平。而东北地区，在工农业恢复带动下，经济增长也有所加快，吉林上半年经济增速达到7.7%。"



        val Item1 = ItemBean(R.drawable.news1,"https://i.imgur.com/x5Hwg6z.png", "中国天眼获得巨大突破", true,example1)
        val Item2 =
            ItemBean(R.drawable.ic_launcher_foreground, "","重庆水文汇报", false,example2)

//        val imageUri = Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F37/ORIGINAL/NONE/687653687")
//        val imagePath = getRealPathFromUri(imageUri)

        val Item3 = ItemBean(R.drawable.new3,
            "https://i.imgur.com/MMEATG8.png","我国成功发射三十六号卫星",true,example3)
        val Item4 = ItemBean(R.drawable.news4,"https://i.imgur.com/Ibh1zlu.png","我国自研海底地震勘探采集装备获突破",true,example4)
        val Item5 = ItemBean(R.drawable.add,"","多地发布上半年地区经济“成绩单” 新动能加速壮大",false,example5)
        for (i in 0 until 5) {
            list.add(Item1)
            list.add(Item2)
            list.add(Item3)
            list.add(Item4)
            list.add(Item5)
        }
        return list
    }

    fun onWhetherClicked(view: View){
        val intent = Intent(this, WheatherActivity::class.java)
        startActivity(intent)
    }

    fun onAddClicked(view: View){
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun onSearchClicked(view: View){
        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
    }

    fun onTopClicked(v: View?) {
        // 滚动到 RecyclerView 的顶部
        val rv_demo = findViewById<RecyclerView>(R.id.rv_demo)
        rv_demo.smoothScrollToPosition(-1000)
    }

//    fun getRealPathFromUri(uri: Uri): String? {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = contentResolver.query(uri, projection, null, null, null)
//        cursor?.let {
//            if (it.moveToFirst()) {
//                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                return it.getString(columnIndex)
//            }
//            cursor.close()
//        }
//        return null
//    }
}