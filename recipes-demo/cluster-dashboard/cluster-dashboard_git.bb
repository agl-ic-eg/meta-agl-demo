SUMMARY     = "Instrument Cluster Dashboard application"
DESCRIPTION = "AGL demonstration instrument cluster dashboard application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-cluster-demo-dashboard"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://app/cluster-gauges.qml;beginline=9;endline=48;md5=54187d50b29429abee6095fe8b7c1a78"

DEPENDS = " \
    qtquickcontrols2 \
    libqtappfw \
    glib-2.0 \
    wayland wayland-native \
    qtwayland qtwayland-native \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-cluster-demo-dashboard;protocol=https;branch=${AGL_BRANCH} \
           file://cluster-dashboard.service \
           file://cluster-dashboard.conf \
           file://cluster-dashboard.token \
"
SRCREV  = "137144c447d8adb618f5acbcbafd65f50264d6eb"

S  = "${WORKDIR}/git"

inherit pkgconfig cmake_qt5 systemd

CLUSTER_DEMO_VISS_HOSTNAME ??= "192.168.10.2"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/cluster-dashboard
    install -m 0644 ${WORKDIR}/cluster-dashboard.conf ${D}${sysconfdir}/xdg/AGL/
    sed -i "s/^server = .*/server = \"${CLUSTER_DEMO_VISS_HOSTNAME}\"/" ${D}${sysconfdir}/xdg/AGL/cluster-dashboard.conf
    install -m 0644 ${WORKDIR}/cluster-dashboard.token ${D}${sysconfdir}/xdg/AGL/cluster-dashboard/
}

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qtquickcontrols \
    qtquickcontrols-qmlplugins \
    qtquickcontrols2 \
    qtquickcontrols2-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtsvg-plugins \
"
