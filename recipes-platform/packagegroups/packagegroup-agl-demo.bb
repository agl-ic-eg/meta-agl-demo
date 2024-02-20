SUMMARY = "The software for AGL IVI DEMO profile"
DESCRIPTION = "A set of packages belong to AGL Demo"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-demo \
    "

# Hook for demo platform configuration
# ATM, only used to disable btwilink module on [MH]3ULCB + Kingfisher by default,
# setting DEMO_ENABLE_BTWILINK to "true" in local.conf / site.conf re-enables.
DEMO_ENABLE_BTWILINK ?= ""
DEMO_PLATFORM_CONF = ""
DEMO_PLATFORM_CONF:append:ulcb = "${@bb.utils.contains("DEMO_ENABLE_BTWILINK", "true", "", " btwilink-disable-conf", d)}"

# fonts
TTF_FONTS = " \
    ttf-bitstream-vera \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    ttf-dejavu-serif \
    ttf-noto-emoji-color \
    source-han-sans-cn-fonts \
    source-han-sans-jp-fonts \
    source-han-sans-tw-fonts \
    source-han-sans-kr-fonts \
    "

RDEPENDS:${PN} += " \
    udisks2 \
    linux-firmware-ath9k \
    linux-firmware-ralink \
    iproute2 \
    ${DEMO_PLATFORM_CONF} \
    ${TTF_FONTS} \
    "
