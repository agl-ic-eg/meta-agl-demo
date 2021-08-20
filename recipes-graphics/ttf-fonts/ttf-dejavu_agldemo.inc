FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
DEPENDS:append = " fontforge-native"

# From the orginal work located at https://aur.archlinux.org/packages/ttf-dejavu-emojiless/

SRC_URI:append = " file://cleaner.py"

do_install:prepend() {
    for TTF in ${S}/*.ttf; do
        fontforge -script ${WORKDIR}/cleaner.py ${TTF}
    done
}
