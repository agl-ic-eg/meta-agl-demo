SUMMARY = "Flutter Dashboard"
DESCRIPTION = "A Flutter based IVI Dashboard Application for automotive grade Linux."
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-dashboard"
SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://License.md;md5=f712ede8d4f845976061925d1416fc40"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-dashboard;protocol=https;branch=${AGL_BRANCH} \
           file://dashboard.yaml \
           file://dashboard.token \
"
SRCREV = "2fbe5f8c19863eb4ac013dce5546900592149a26"

S = "${WORKDIR}/git"

inherit agl-app flutter-app

# flutter-app
#############
PUBSPEC_APPNAME = "dashboard_app"
FLUTTER_APPLICATION_INSTALL_PREFIX = "/flutter"
FLUTTER_BUILD_ARGS = "bundle -v"

# agl-app
#########
AGL_APP_TEMPLATE = "agl-app-flutter"
AGL_APP_ID = "dashboard_app"
AGL_APP_NAME = "Dashboard"

do_install:append() {
    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/dashboard
    install -m 0644 ${WORKDIR}/dashboard.yaml ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/dashboard.token ${D}${sysconfdir}/xdg/AGL/dashboard/
}

FILES:${PN} += "${sysconfdir}/xdg/AGL"
RDEPENDS:${PN} += "flutter-auto"

