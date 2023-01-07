package cn.ln28.rkgpio

object TestRK {
    @JvmStatic
    fun main(args: Array<String>) {
        val groupValue = RK_GPIO_GROUP.GPIO1.value
        val pinValue = RK_GPIO_PIN.C2.value
        val gpioNumber = groupValue + pinValue
        println("GPIO PIN:" + gpioNumber)
        val pinVal = (gpioNumber % 32)
        val pinName = getPinName(pinVal)
        val grounVal = gpioNumber - (gpioNumber % 32)
        val groupName = getGroupName(grounVal)
        println("对应DTS 1:" + groupName + "_" + pinVal)
        println("对应DTS 2:" + groupName + "_" + pinName)

        for (mapping in RK_GPIO_MAPPING.values()) {
            println("${mapping.name} 基地址: io -4 -l 8 ${"0x%X".format(mapping.value)}")
        }

        //D7-D0     C7-C0    B7-B0    A7-A0
        val gpio4_c5_h = 0x62600000  //01100010 01100000 00000000 00000000  //GPIO4_C5高电平 假设这是io -4 -l 8 0xff790000读出来的值
        val gpio4_c5_l = 0x62400000  //01100010 01000000 00000000 00000000  //GPIO4_C5低电平
        val gpio4_direction = 0x6f600000
        val gpio4_c5_h_bin = gpio4_c5_h.toString(2).padStart(32, '0')
        val gpio4_c5_l_bin = gpio4_c5_l.toString(2).padStart(32, '0')
        val gpio4_direction_bin = gpio4_direction.toString(2).padStart(32, '0')
        println("${groupName + "_" + pinName}高电平二进制:${gpio4_c5_h_bin} , 长度:${gpio4_c5_h_bin.length}")
        println("${groupName + "_" + pinName}低电平二进制:${gpio4_c5_l_bin} , 长度:${gpio4_c5_l_bin.length}")
        println("${groupName}     方向二进制:${gpio4_direction_bin} , 长度:${gpio4_direction_bin.length}")
        println(
            "设置${groupName + "_" + pinName}IO高电平命令: io -4 -w ${"0x%X".format(RK_GPIO_MAPPING.PMUGRF_GPIO4_MAPPING.value)} ${
                "0x%X".format(
                    gpio4_c5_h
                )
            }"
        )
        println(
            "设置${groupName + "_" + pinName}IO低电平命令: io -4 -w ${"0x%X".format(RK_GPIO_MAPPING.PMUGRF_GPIO4_MAPPING.value)} ${
                "0x%X".format(
                    gpio4_c5_l
                )
            }"
        )
    }
    /*
    * ff720000:  00000600 00000e02
    * ff730000:  00000000 00064000
    * ff780000:  0c080011 0c080011
    * ff788000:  00000000 00000000
    * ff790000:  44200000 44200000
    */
    fun getGroupName(value: Int): String {
        for (group in RK_GPIO_GROUP.values()) {
            if (group.value == value) {
                return group.name
            }
        }
        return "";
    }

    fun getPinName(value: Int): String {
        for (pin in RK_GPIO_PIN.values()) {
            if (pin.value == value) {
                return pin.name
            }
        }
        return "";
    }

}

