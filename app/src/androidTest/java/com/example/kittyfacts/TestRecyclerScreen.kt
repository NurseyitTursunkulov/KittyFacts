package com.example.kittyfacts

import android.view.View
import com.agoda.kakao.check.KCheckBox
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

open class TestRecyclerScreen : Screen<TestRecyclerScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.news_list)
    }, itemTypeBuilder = {
        itemType(::MainItem)
    })

    class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.title) }
        val subtitle: KTextView = KTextView(parent) { withId(R.id.detail_text_view) }
    }

}
