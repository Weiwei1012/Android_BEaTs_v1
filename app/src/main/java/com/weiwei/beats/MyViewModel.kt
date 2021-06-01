package com.weiwei.beats

import android.app.Application
import androidx.lifecycle.*
import com.weiwei.beats.database.Restaurant
import com.weiwei.beats.database.RestaurantDatabase
import com.weiwei.beats.weather.Temperature
import kotlinx.coroutines.launch

//viewmodel need to know the database dao (passed argument here) for accessing data from the database
class MyViewModel(application: Application) : AndroidViewModel(application) {
    //get the reference to the database dao
    private val database = RestaurantDatabase.getInstance(application).restaurantDatabaseDao

    //a list of all scenes or matched scenes from the database (LiveData)
    val sceneList = MediatorLiveData<List<Restaurant>>()

    //get the selected scene (livedata for easy observing)
    val selectedScene = MutableLiveData<Restaurant>()

    val cityList = arrayOf(
        "板橋","新莊","土城"
    )

    init {
        getAllScenes()
    }

    fun getAllScenes() {  //set the livedata source of the sceneList to be all scenes
        sceneList.addSource(database.loadAllScenes()) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun searchScene(name: String) { //set the livedata source of the sceneList to be matched scenes
        sceneList.addSource(database.findScenes(name)) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun getScene(sceneId: Long) {  //get the scene from the database by given its id
        viewModelScope.launch {
            selectedScene.value = database.loadOneScene(sceneId)
        }
    }

    fun insertScene(newScene: Restaurant) {  //add a new scene into the database
        viewModelScope.launch {
            database.insertScene(newScene)
        }
    }

    fun updateScene(oldScene: Restaurant) {  //add a new scene into the database
        viewModelScope.launch {
            database.updateScene(oldScene)
        }
    }

    fun deleteScene(oldScene: Restaurant) {  //delete a scene from the database
        viewModelScope.launch {
            database.deleteScene(oldScene)
        }
    }


    fun initDB() {  //setup the initial database
        viewModelScope.launch {
            database.insertScene(
                Restaurant(
                    "板橋",
                    "吳鳳路傳統豆漿店",
                    "220新北市板橋區吳鳳路50巷18號",
                    R.drawable.restaurant9,
                    "「吳鳳路傳統豆漿店」是家老字號的江子翠早餐，開業至今已經三十多年，最為人津津樂道的就是這裡的蛋餅超過三十種口味，而且持續增加中，號稱板橋蛋餅王"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "好初早餐",
                    "220新北市板橋區文化路二段125巷70號",
                    R.drawable.restaurant11,
                    "食尚玩家之超人氣早午餐！！想要吃，又不想像我排了兩小時隊的朋友建議在開店前就先到現場報到，擠進第一輪的列車就不用經過漫長的等待囉！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "太和豆漿",
                    "220新北市板橋區民治街69號",
                    R.drawable.restaurant10,
                    "傳說中的海景饅頭包著豬排、蔥蛋、油條、火腿、起司、花生醬、油條，脆脆的花生醬是有顆粒的每一口都是驚喜，很有層次感"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "巧園雞肉飯",
                    "220新北市板橋區懷德街80號",
                    R.drawable.restaurant13,
                    "雞肉飯有嚼勁不乾柴，滷肉飯大塊軟嫩，米煮的彈性適中，豆干微辣口味特殊，總結：讚！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "Yuly - Brunch & Coffee",
                    "220新北市板橋區雙十路三段10巷16號",
                    R.drawable.restaurant5,
                    "餐點頗具用心，而且價位不高，想在早晨享用頓美味豐盛的早午餐，yuly是個不錯的選擇～"
                )
            )
            database.insertScene(
                Restaurant(
                    "新莊",
                    "Gourmet Pasta",
                    "242新北市新莊區中正路514巷47號",
                    R.drawable.restaurant8,
                    "口味確實在輔大這邊的義大利麵來說是數一數二，價格合理沒問題，店內有免費無限暢飲濃湯和飲料(紅茶&蜂蜜檸檬)"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "莊敬路263巷口牛肉麵",
                    "220新北市板橋區莊敬路263巷8號",
                    R.drawable.restaurant19,
                    "這是當地人喜歡去的牛肉專賣店。湯底很好吃，可以一直續湯而且麵條種類很多。僅僅麵條的量就相當於普通牛肉麵條店的兩倍。單就價格來說很不錯。它藏在巷子裡。總體而言，一家不錯的店。"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "甜福。貳\nFUKU Brunch早午餐",
                    "220新北市板橋區公館街86號",
                    R.drawable.restaurant2,
                    "精緻的網美早午餐店。價格合理，餐點份量也不會太少，適合愛拍照打卡且肚子很餓的朋友來嚐嚐！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "潮粥府沙鍋粥",
                    "220新北市板橋區文化路二段419號",
                    R.drawable.restaurant18,
                    "位於板橋江子翠的『潮粥府沙鍋粥』，提供特色料理沙鍋粥，由現撈海產搭配生米熬煮成粥，像是鮑魚粥、蟹膏粥、龍蝦粥，將海鮮的鮮甜，融入粥底，適合混搭店內各種熱炒小菜都非常對味。"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "楊家紅豆餅",
                    "220新北市板橋區文化路二段124巷28號",
                    R.drawable.restaurant16,
                    "必吃奶油餅！非常好吃，現點現做，不論是平日或假日都是大排長龍而且一下子就會賣完！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋區",
                    "起家厝\nKhi Ke Thsu Cafè",
                    "220新北市板橋區松江街118號",
                    R.drawable.restaurant23,
                    "一間漂亮的早午餐店，食物非常好吃且乾淨衛生，店內的氣氛也非常的舒適！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "定光 韓食輕飲",
                    "220新北市板橋區西門街4號",
                    R.drawable.restaurant12,
                    "CP值極高的韓式餐廳！部隊鍋份量非常足夠，而生菜包肉也是一等一的美味，餐點也都非常的便宜！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "Lazy Pig Sandwich\n三明治專賣店",
                    "220新北市板橋區文化路二段410巷2弄3號1樓",
                    R.drawable.restaurant4,
                    "店內裝潢非常可愛，三明治也非常好吃，並有提供雜誌閱讀以及毛絨玩具，是個適合親子用餐的地方！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "泰之初Brunch\n江翠店",
                    "220新北市板橋區松柏街61號",
                    R.drawable.restaurant17,
                    "捷運江子翠站附近松柏街上的「泰之初」供應的泰式早午餐非常特別！「泰之初」供應的泰式料理餐點類型相當豐富，各道餐點的口味都很不錯。"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "IT'S就是義麵屋",
                    "220新北市板橋區文化路二段182巷7弄17號",
                    R.drawable.restaurant21,
                    "從小吃到大的義大利麵，好吃且價格合理，餐點選擇豐富！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "茶朵木",
                    "220新北市板橋區莒光路59號",
                    R.drawable.restaurant22,
                    "看似平凡無奇的飲料店，卻常常有許多在地人點店內的「紅茶拿鐵加波霸」，可見店家魅力不同凡響！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "成昌食堂",
                    "220新北市板橋區中山路二段157號",
                    R.drawable.restaurant14,
                    "非常具有古色古香的老店，老饕必點控肉！不論平日或假日都是大排長龍！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "雅米早餐",
                    "220新北市板橋區中山路二段82號",
                    R.drawable.restaurant6,
                    "份量非常大的早午餐店，餐點好吃且服務生態度非常好。時常會有抽獎活動，可以去試試運氣！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋區",
                    "石二鍋",
                    "220新北市板橋區文化路一段360號",
                    R.drawable.restaurant20,
                    "雖然是一間連鎖的火鍋店，但食物都非常的新鮮且美味，對於學生來說是再好不過的小火鍋選擇！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "9% 酒趴串燒Bar",
                    "220新北市板橋區文化路二段357號",
                    R.drawable.restaurant1,
                    "店內非常多的調酒及炸物，也有飛鏢機讓你與朋友們盡情放鬆玩耍！"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "星馬廚房",
                    "220新北市板橋區文化路一段311之2號",
                    R.drawable.restaurant15,
                    "好食物 會上癮 小地方 大味道 我們不是大廚師 只是愛料理 沒有浮誇的花樣 只有真心呈現 用心烹調 沒有山珍海味 只有傳統 溫暖道地的家鄉味"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "勇氣食堂",
                    "新北市板橋區文化路一段270巷6號",
                    R.drawable.restaurant7,
                    "隱身巷弄清新有質感的日式簡餐店！金黃酥脆堆得像座小山的日式炸雞，再配上韓式泡菜，超誘人搭配讓人一口接一口!"
                )
            )
            database.insertScene(
                Restaurant(
                    "板橋",
                    "G+9鮮釀餐廳",
                    "220新北市板橋區國光路232號",
                    R.drawable.restaurant3,
                    "平價好吃的餐酒館！地點方便，位置也滿靠火車站的。當月壽星有招待一杯飲料，可選擇無酒精或店家限定的幾種啤酒。"
                )
            )
        }
    }
}