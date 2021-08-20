SUMMARY = "The ttf fonts for AGL"
DESCRIPTION = "A set of packages for fonts"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
     packagegroup-agl-ttf-fonts \
    "

ALLOW_EMPTY:${PN} = "1"


# fonts
RDEPENDS:${PN}:append = " \
    ttf-bitstream-vera \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    ttf-dejavu-serif \
"
