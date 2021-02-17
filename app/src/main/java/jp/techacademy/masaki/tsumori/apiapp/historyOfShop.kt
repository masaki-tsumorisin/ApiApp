package jp.techacademy.masaki.tsumori.apiapp

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class historyOfShop: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var url: String = ""

    companion object {
        fun findAll(): List<historyOfShop> = // お気に入りのShopを全件取得
            Realm.getDefaultInstance().use { realm ->
                realm.where(historyOfShop::class.java)
                    .findAll().let {
                        realm.copyFromRealm(it)
                    }
            }

        fun findBy(id: String): historyOfShop? = // お気に入りされているShopをidで検索して返す。お気に入りに登録されていなければnullで返す
            Realm.getDefaultInstance().use { realm ->
                realm.where(historyOfShop::class.java)
                    .equalTo(historyOfShop::id.name, id)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        fun findByURL(url: String): historyOfShop? = // お気に入りされているShopをidで検索して返す。お気に入りに登録されていなければnullで返す
            Realm.getDefaultInstance().use { realm ->
                realm.where(historyOfShop::class.java)
                    .equalTo(historyOfShop::url.name, url)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        fun insert(favoriteShop: historyOfShop) = // お気に入り追加
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(favoriteShop)
            }

        fun delete(id: String) = // idでお気に入りから削除する
            Realm.getDefaultInstance().use { realm ->
                realm.where(historyOfShop::class.java)
                    .equalTo(historyOfShop::id.name, id)
                    .findFirst()?.also { deleteShop ->
                        realm.executeTransaction {
                            deleteShop.deleteFromRealm()
                        }
                    }
            }
    }
}