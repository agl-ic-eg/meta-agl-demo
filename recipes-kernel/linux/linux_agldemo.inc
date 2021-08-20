FILESEXTRAPATHS:prepend := "${THISDIR}/linux:"

# Enable assorted USB device support for demo systems
AGL_KCONFIG_FRAGMENTS += " \
    usb.cfg \
    uvc.cfg \
    hid.cfg \
    joystick.cfg \
    usbaudio.cfg \
    usbmodem.cfg \
    btusb.cfg \
    i2c-led.cfg \
"

# Enable CAN device support
AGL_KCONFIG_FRAGMENTS += "can-devices.cfg"

# Enable NFC support
AGL_KCONFIG_FRAGMENTS += "nfc.cfg"

# Enable support for TP-Link TL-W722N USB Wifi adapter (rev 1 and 2)
AGL_KCONFIG_FRAGMENTS += "wifi.cfg"

# Enable support for RTLSDR
AGL_KCONFIG_FRAGMENTS += "rtl_sdr.cfg"

# iio-dummy-device support for test IIO device
AGL_KCONFIG_FRAGMENTS += "iiodevice.cfg"

# External rtc support via e.g. http://wiki.seeedstudio.com/Grove-RTC/
AGL_KCONFIG_FRAGMENTS += "rtc.cfg"

# Make sure in-kernel MOST drivers are disabled to avoid conflicting
# with our externally built ones
AGL_KCONFIG_FRAGMENTS += "disable_most.cfg"

# Enable some things on qemuarm64 so MOST drivers will build and load.
AGL_KCONFIG_FRAGMENTS:append:qemuarm64 = " most_deps.cfg"
