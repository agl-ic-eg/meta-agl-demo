SUMMARY     = "Instrument Cluster receiver application"
DESCRIPTION = "AGL demonstration instrument cluster XDG remote display application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-cluster-demo-receiver"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=374fee6a7817f1e1a5a7bfb7b7989553"

DEPENDS = " \
    wayland wayland-native \
    agl-compositor \
    gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-cluster-demo-receiver;protocol=https;branch=${AGL_BRANCH} \
           file://cluster-receiver.service \
"
SRCREV  = "3695f21dac73fb1da6a48489c86e1920e4acfe75"

S  = "${WORKDIR}/git"

inherit cmake pkgconfig

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_user_unitdir}/${BPN}.service
    install -d ${D}${systemd_user_unitdir}/agl-session.target.wants
    ln -s ../${BPN}.service ${D}${systemd_user_unitdir}/agl-session.target.wants/${BPN}.service
}

FILES:${PN} += " ${systemd_user_unitdir}"

RDEPENDS:${PN} += " \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"
