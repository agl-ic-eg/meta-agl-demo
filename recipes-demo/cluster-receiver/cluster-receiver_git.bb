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
SRCREV  = "8633f9754fc0b644e45862fc3ed6f95554ce58cf"

S  = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
}

RDEPENDS:${PN} += " \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"
