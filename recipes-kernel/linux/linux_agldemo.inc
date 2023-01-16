FILESEXTRAPATHS:prepend := "${THISDIR}/linux:"

# Enable assorted USB device support for demo systems
AGL_KCONFIG_FRAGMENTS_DEMO += " \
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
AGL_KCONFIG_FRAGMENTS_DEMO += "can-devices.cfg"

# Enable NFC support
AGL_KCONFIG_FRAGMENTS_DEMO += "nfc.cfg"

# Enable support for TP-Link TL-W722N USB Wifi adapter (rev 1 and 2)
AGL_KCONFIG_FRAGMENTS_DEMO += "wifi.cfg"

# Enable support for RTLSDR
AGL_KCONFIG_FRAGMENTS_DEMO += "rtl_sdr.cfg"

# iio-dummy-device support for test IIO device
AGL_KCONFIG_FRAGMENTS_DEMO += "iiodevice.cfg"

# External rtc support via e.g. http://wiki.seeedstudio.com/Grove-RTC/
AGL_KCONFIG_FRAGMENTS_DEMO += "rtc.cfg"

# Make sure in-kernel MOST drivers are disabled to avoid conflicting
# with our externally built ones
AGL_KCONFIG_FRAGMENTS_DEMO += "disable_most.cfg"

# Enable some things on qemuarm64 so MOST drivers will build and load.
AGL_KCONFIG_FRAGMENTS_DEMO:append:qemuarm64 = " most_deps.cfg"

# We don't want most of the above for the virtio guest kernels,
# use an explicit list
AGL_KCONFIG_FRAGMENTS_DEMO:virtio-all = " \
    kvm-demo-can-devices.cfg \
    kvm-demo-usb.cfg \
    usbaudio.cfg \
    hid.cfg \
"

AGL_KCONFIG_FRAGMENTS += "${AGL_KCONFIG_FRAGMENTS_DEMO}"