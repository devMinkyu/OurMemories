package com.kotlin.ourmemories.view.place

/**
 * Created by hee on 2017-11-28.
 */
import java.util.*

object AddressBook {

    fun makeAddress(): List<SiDo> {
        return Arrays.asList(
                makeSeoul(),
                makeGyeonggi(),
                makeChungcheong(),
                makeJeolla(),
                makeGyeongsang(),
                makeGangwon(),
                makeJeju()
        )
    }


    fun makeSeoul(): SiDo {
        return SiDo("서울특별시", makeSeoulSubAddress())
    }

    fun makeSeoulSubAddress(): List<SiGunGu> {
        val Seoul1 = SiGunGu("강남구")
        val Seoul2 = SiGunGu("강동구")
        val Seoul3 = SiGunGu("강북구")
        val Seoul4 = SiGunGu("강서구")
        val Seoul5 = SiGunGu("관악구")
        val Seoul6 = SiGunGu("광진구")
        val Seoul7 = SiGunGu("구로구")
        val Seoul8 = SiGunGu("금천구")
        val Seoul9 = SiGunGu("노원구")
        val Seoul10 = SiGunGu("도봉구")
        val Seoul11 = SiGunGu("동대문구")
        val Seoul12 = SiGunGu("동작구")
        val Seoul13 = SiGunGu("마포구")
        val Seoul14 = SiGunGu("서대문구")
        val Seoul15 = SiGunGu("서초구")
        val Seoul16 = SiGunGu("성동구")
        val Seoul17 = SiGunGu("성북구")
        val Seoul18 = SiGunGu("송파구")
        val Seoul19 = SiGunGu("양천구")
        val Seoul20 = SiGunGu("영등포구")
        val Seoul21 = SiGunGu("용산구")
        val Seoul22 = SiGunGu("은평구")
        val Seoul23 = SiGunGu("종로구")
        val Seoul24 = SiGunGu("중구")
        val Seoul25 = SiGunGu("중랑구")

        return Arrays.asList(Seoul1, Seoul2, Seoul3, Seoul4, Seoul5,
                Seoul6, Seoul7, Seoul8, Seoul9, Seoul10,
                Seoul11, Seoul12, Seoul13, Seoul14, Seoul15,
                Seoul16, Seoul17, Seoul18, Seoul19, Seoul20,
                Seoul21, Seoul22, Seoul23, Seoul24, Seoul25)
    }

    fun makeGyeonggi(): SiDo {
        return SiDo("경기도", makeGyeonggiSubAddress())
    }

    fun makeGyeonggiSubAddress(): List<SiGunGu> {
        val Gyeonggi1 = SiGunGu("경기도")
        val Gyeonggi2 = SiGunGu("인천광역시")

        return Arrays.asList(Gyeonggi1, Gyeonggi2)
    }

    fun makeChungcheong(): SiDo {
        return SiDo("충청도", makeChungcheongSubAddress())
    }

    fun makeChungcheongSubAddress(): List<SiGunGu> {
        val Chungcheong1 = SiGunGu("대전광역시")
        val Chungcheong2 = SiGunGu("세종특별자치시")
        val Chungcheong3 = SiGunGu("충청북도")
        val Chungcheong4 = SiGunGu("충청남도")


        return Arrays.asList(Chungcheong1, Chungcheong2, Chungcheong3, Chungcheong4)
    }

    fun makeJeolla(): SiDo {
        return SiDo("전라도", makeJeollaSubAddress())
    }

    fun makeJeollaSubAddress(): List<SiGunGu> {
        val Jeolla1 = SiGunGu("광주광역시")
        val Jeolla2 = SiGunGu("전라북도")
        val Jeolla3 = SiGunGu("전라남도")

        return Arrays.asList(Jeolla1, Jeolla2, Jeolla3)
    }

    fun makeGyeongsang(): SiDo {
        return SiDo("경상도", makeChungcheongSubAddress())
    }

    fun makeGyeongsangSubAddress(): List<SiGunGu> {
        val Gyeongsang1 = SiGunGu("부산광역시")
        val Gyeongsang2 = SiGunGu("대구광역시")
        val Gyeongsang3 = SiGunGu("울산광역시")
        val Gyeongsang4 = SiGunGu("경상북도")
        val Gyeongsang5 = SiGunGu("경상남도")

        return Arrays.asList(Gyeongsang1, Gyeongsang2, Gyeongsang3, Gyeongsang4, Gyeongsang5)
    }

    fun makeGangwon(): SiDo {
        return SiDo("강원도", makeGangwonSubAddress())
    }

    fun makeGangwonSubAddress(): List<SiGunGu> {
        val Gangwon1 = SiGunGu("강원도")

        return Arrays.asList(Gangwon1)
    }
    fun makeJeju(): SiDo {
        return SiDo("제주도", makeJejuSubAddress())
    }

    fun makeJejuSubAddress(): List<SiGunGu> {
        val  Jeju1 = SiGunGu("제주도")

        return Arrays.asList(Jeju1)
    }

}