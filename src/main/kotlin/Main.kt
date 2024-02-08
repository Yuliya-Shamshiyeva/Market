
class Interaction(){
    private val basket = Basket(this)
    private val productList = ProductList(this)
    private val admin = Admin(this)
    fun getProductList(): MutableList<Data> {
        return ImportData.productListx
    }
    fun name(){
        println("Введите имя:")
    }
    fun welcomemsg(name:String){
        if (name.trim().isNotEmpty()) {
            println("${name.trim()}, добро пожаловать в интернет магазин товаров для дома!")
            if (name == "admin") {
                println("Вы вошли в режим администратора!")
                admin.print()
            } else {
                startMenu()
            }
        }else{
            println("Ошибка. Вы ввели пустое значение")
            main()
        }

    }
    fun adminStartMenu(){
        do {
            println(
                "Введите-'1', для ввода новых позиций в интернет магазин\n" +
                        "Введите-'2', для удаления позиции из интернет магазина\n" +
                        "Введите-'3', для выхода из режима администратора"
            )
            val startNext = readln().trim().toString()
            when (startNext) {
                "1" -> admin.add()
                "2" -> admin.delete()
                "3" -> main()
                else -> println("Ошибка. Повторите действие")
            }
        }while (startNext!="1" || startNext!="2" || startNext!="3")
    }
    fun startMenu(){
        do {
            println(
                "Введите-'1', для перехода в список товаров\n" +
                "Введите-'2', для перехода в корзину с товарами\n" +
                "Введите-'3', для выхода из магазина"
            )
            val startNext = readln().trim().toString()
            when (startNext) {
                "1" -> productList.list()
                "2" -> basket.list()
                "3" -> exit()
                else -> println("Ошибка. Повторите действие")
            }
        }while (startNext!="1" || startNext!="2" || startNext!="3")
    }
    fun productListMenu(){
        do {
            println("Введите-'+', для добавления в корзину\n" +
                    "Введите-'p', для поиска товара в списке\n" +
                    "Введите-'f', для фильтрации товара по цене\n"+
                    "Введите-'k', для перехода в корзину с товарами\n" +
                    "Введите-'m', для выхода в главное меню\n")
            val action = readln().toString().trim()
                when (action) {
                    "+" -> basket.add()
                    "p" -> productList.search()
                    "р" -> productList.search()
                    "f" -> productList.filter()
                    "k" -> basket.list()
                    "m" -> startMenu()
                    else -> println("Ошибка. Повторите действие")
                }
        }while (action!="+" || action!="p" || action!="f" || action!="k" || action!="m")
    }
    fun basketMenu(){
        do{
            println("Введите-'-', для удаления из корзины\n" +
                    "Введите-'p', для поиска товара в корзине\n" +
                    "Введите-'f', для фильтрации товара по цене\n"+
                    "Введите-'s', для перехода в список товаров\n" +
                    "Введите-'m', для выхода в главное меню\n"+
                    "Введите-'all', для полной очистки корзины\n")
            val action = readln().toString().trim()
            when (action) {
                "-" -> basket.delete()
                "p" -> basket.search()
                "р" -> basket.search()
                "f" -> basket.filter()
                "s" -> productList.list()
                "m" -> startMenu()
                "all" -> basket.deleteAll()
                else -> println("Ошибка. Повторите действие")
            }
        }while (action!="-" || action!="p" || action!="f" || action!="s" || action!="m" || action!="all")
    }
    fun searchBasketMenu(){
        do{
            println("Введите-'-', для удаления этого товара из корзины\n" +
                    "Введите-'k', для перехода ко всем товарам из корзины\n" +
                    "Введите-'s', для перехода в список товаров\n")
            val action = readln().toString().trim()
            when (action) {
                "-" -> basket.delete()
                "k" -> basket.list()
                "s" -> productList.list()
                else -> println("Ошибка. Повторите действие")
            }
        }while (action!="-" || action!="k" || action!="s")
    }
    private fun exit(){
        println("До новых покупок!")
        System.exit(0)
    }
    fun emptyBasket(){
        println("Корзина пустая\nВведите-'0', для перехода к списку товаров в магазине")
        do{
            val action = readln().toString().trim()
            when (action) {
                "0" -> productList.list()
                else -> println("Ошибка. Повторите действие")
            }
        }while (action!="0")
    }
}
class ProductList(private val interaction: Interaction){
    val product = interaction.getProductList()
    var switch= true
    fun list(){
        println("Список товаров: ")
        println("№ | id   | Категория товара    | Наименование          | Цена")
        for ((index,product) in product.withIndex()) {
            println("${index+1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
        }
        interaction.productListMenu()
    }
    fun search(){
        println("Введите название товара, который вы ищите: ")
        val idsearchProduct = readln().toString().trim().lowercase()
        val foundProduct = product.find { it.productname.trim().lowercase() == idsearchProduct }
        if (foundProduct != null) {
            println("Вот, что мы нашли по вашему запросу: ")
            for ((index,product) in product.filter { it.productname.trim().lowercase() == idsearchProduct.trim().lowercase() }.withIndex()) {
                println("${index + 1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
        } else {
            println("К сожалению этого у нас нет")
        }

        interaction.productListMenu()
    }
    fun filter(){
        if (switch == true){
            for ((index,product) in product.sortedBy{it.productprice}.withIndex()){
                println("${index+1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
            switch = false
        }else{
            for ((index,product) in product.sortedByDescending{it.productprice}.withIndex()){
                println("${index+1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
            switch = true
        }
        interaction.productListMenu()
    }
}
class Basket(private val interaction: Interaction){
    val product = interaction.getProductList()
    private var basketlistOfProduct = mutableListOf<Data>()
    private var switch= true
    fun list(){
        println("Корзина: ")
        if (basketlistOfProduct.isEmpty()) {
            interaction.emptyBasket()
        }else{
            println("№ | id   | Категория товара    | Наименование          | Цена")
            for ((index,product) in basketlistOfProduct.withIndex()) {
                println("${index+1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
            sum()
            interaction.basketMenu()
        }
    }
    fun search(){
        println("Введите название товара, который вы ищите: ")
        val idsearchProduct = readln().toString().trim().lowercase()
        val foundProduct = basketlistOfProduct.find { it.productname.trim().lowercase() == idsearchProduct }
        if (foundProduct != null) {
            println("Вот, что мы нашли по вашему запросу: ")
            for ((index,product) in basketlistOfProduct.filter { it.productname.trim().lowercase() == idsearchProduct.trim().lowercase() }.withIndex()) {
                println("${index + 1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
        } else {
            println("В вашей корзине отсутствует товар $idsearchProduct")
        }
        interaction.searchBasketMenu()
    }
    fun filter(){
        if (switch == true){
            for ((index,product) in basketlistOfProduct.sortedBy{it.productprice}.withIndex()) {
                println("${index + 1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
            switch = false
        }else{
            for ((index,product) in basketlistOfProduct.sortedByDescending{it.productprice}.withIndex()) {
                println("${index + 1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
            }
            switch = true
        }
        interaction.basketMenu()
    }
    fun add(){
        println("Введите ID товара, который вы хотите добавить: ")
        val idProduct = readln().toString().trim().lowercase()
        val foundProduct = product.find { it.id.trim().lowercase() == idProduct }
        if (foundProduct != null) {
            basketlistOfProduct.add(foundProduct)
            println("Товар $idProduct добавлен в корзину!")
        } else {
            println("Ошибка. Товара с таким ID: $idProduct нет")
        }
        interaction.productListMenu()

    }
    fun delete(){
        println("Введите ID товара, который вы хотите удалить: ")
        val idProduct = readln().toString().trim().lowercase()
        val foundProduct = basketlistOfProduct.find { it.id.trim().lowercase() == idProduct }
        if (foundProduct != null) {
            basketlistOfProduct.remove(foundProduct)
            println("Товар $idProduct удален из корзины!")
        }else {
            println("Ошибка. Товара с таким ID: $idProduct нет в корзине")
        }
        list()
    }
    fun deleteAll(){
        basketlistOfProduct.clear()
        println("Корзина очищена!")
        list()
    }
    private fun sum(){
        val sum = basketlistOfProduct.sumOf { it.productprice }
        println("                                                  Сумма: $sum")
    }
}

class Admin(private val interaction: Interaction){
    val product = interaction.getProductList()
    fun print(){
        println("№ | id   | Категория товара    | Наименование          | Цена")
        for ((index,product) in product.withIndex()) {
            println("${index+1} | ${product.id} | ${product.productcategory} | ${product.productname} | ${product.productprice}")
        }
        Interaction().adminStartMenu()
    }
    fun add(){
        var id: Int = 0
        while (id==0 && id.toString().length!=4){
            try {
                println("Введите id: ")
                val input = readln().trim()
                val number = input.toInt()
                if (input.length == 4) {
                    id = number
                } else {
                    id = 0
                    throw NumberFormatException("Ошибка. Введенное ID не верного формата. Введите 4-х значное число")
                }
            } catch (e: NumberFormatException) {
                println(e.message)
                }
        }
        println("Введите категорию товара: ")
        val productcategory = readln().trim().toString()
        println("Введите наименование товара: ")
        val productname = readln().trim().toString()
        var productprice=0.0
        while (productprice <= 0){
            try {
                println("Введите цену товара: ")
                val input = readln().trim().toDouble()
                if (input>0) {
                    productprice = input
                } else {
                    throw NumberFormatException("Ошибка. Цена не может быть отрицательной или нулем")
                }
            } catch (e: NumberFormatException) {
                println(e.message)
            }
        }

        product.add(Data(id.toString(),productcategory,productname,productprice))
        println("Новая позиция успешно добавлена!")
        print()
    }
    fun delete(){
        println("Введите ID товара, который вы хотите удалить: ")
        val idProduct = readln().toString().trim().lowercase()
        val foundProduct = product.find { it.id.trim().lowercase() == idProduct }
        if (foundProduct != null) {
            product.remove(foundProduct)
            println("Товар $idProduct удален из корзины!")
        }else {
            println("Ошибка. Товар с таким ID: $idProduct отсутствует")
        }
        print()
    }
}
class Data(val id:String, val productcategory: String,val productname: String, val productprice: Double){
}
object ImportData{
    val productListx = mutableListOf(
        Data("1231","Товары для кухни   ","Контейнер для сыпучих",2000.0),
        Data("2341","Товары для кухни   ","Подставка под горячее",1700.0),
        Data("3451","Товары для кухни   ","Тарелка              ",1500.0),
        Data("4561","Товары для гостиной","Ваза                 ",5000.0),
        Data("5672","Товары для гостиной","Плед                 ",6000.0),
        Data("6782","Товары для гостиной","Подсвечник           ",8000.0),
        Data("7893","Товары для спальни ","Постельное белье     ",12000.0),
        Data("8905","Товары для спальни ","Подушка              ",5000.0),
        Data("9018","Товары для спальни ","Покрывало            ",28000.0))
}
fun main() {
    val interaction = Interaction()
    interaction.name()
    val name = readln().toString()
    interaction.welcomemsg(name)
}

