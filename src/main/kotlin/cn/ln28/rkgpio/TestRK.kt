package cn.ln28.rkgpio

object TestRK {
    @JvmStatic
    fun main(args: Array<String>) {
        val groupValue = RK_GPIO_GROUP.GPIO4.value
        val pinValue = RK_GPIO_PIN.D7.value
        val gpioNumber = groupValue + pinValue

        System.out.println(gpioNumber)

        val pinVal = (gpioNumber % 32)
        val pinName = getPinName(pinVal)
        val grounVal = gpioNumber - (gpioNumber % 32)
        val groupName = getGroupName(grounVal)
        System.out.println(groupName + "_" + pinVal)
        System.out.println(groupName + "_" + pinName)

        val IOMUX = RK_GPIO_IOMUX.PMUGRF_GPIO1D_IOMUX.value
        val GRF = 0xff770000
        val s = "0x%X".format(GRF + IOMUX)
        System.out.println(s)

        //D7-D0     C7-C0    B7-B0    A7-A0
        val gpio4_c5_h = 0x62600000  //01100010 01100000 00000000 00000000  //GPIO4_C5高电平
        val gpio4_c5_l = 0x6f600000 //01100010 01000000 00000000 00000000  //GPIO4_C5低电平
        //11011110 11000000 00000000 0000000
        //01101111 01100000 00000000 00000000
        val gpio4_c5_h_bin = gpio4_c5_h.toString(2).padStart(32, '0')
        val gpio4_c5_l_bin = gpio4_c5_l.toString(2).padStart(32, '0')
        println(gpio4_c5_h_bin + "," + gpio4_c5_h_bin.length)
        println(gpio4_c5_l_bin + "," + gpio4_c5_l_bin.length)
    }

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

