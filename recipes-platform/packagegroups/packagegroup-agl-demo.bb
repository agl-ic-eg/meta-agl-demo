SUMMARY = "The software for AGL IVI DEMO profile"
DESCRIPTION = "A set of packages belong to AGL Demo"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-demo \
    "

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
    ${TTF_FONTS} \
    "
