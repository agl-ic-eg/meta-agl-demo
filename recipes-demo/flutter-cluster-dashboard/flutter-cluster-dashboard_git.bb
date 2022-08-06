SUMMARY = "Flutter Instrument Cluster "
DESCRIPTION = "An instrument cluster app written in dart for the flutter runtime"
AUTHOR = "Aakash Solanki"
HOMEPAGE = "https://github.com/aakash-s45/ic"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0c52b0e4b5f0dbf57ea7d44bebb2e29d"

SRC_URI = "git://github.com/aakash-s45/ic;protocol=https;branch=main \
    file://flutter-cluster-dashboard.service \
    file://ic_on_bg-debug.json \
    file://ic_on_bg-profile.json \
    file://ic_on_bg-release.json \
"

PV = "1.0+git${SRCPV}"
SRCREV = "32f72bb68142a0dbde9ccf28b27c445598c95112"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "ic"

FLUTTER_APPLICATION_INSTALL_PREFIX = "/flutter"

inherit flutter-app

APP_CONFIG = "ic_on_bg-release.json"
APP_CONFIG:class-runtimedebug = "ic_on_bg-debug.json"
APP_CONFIG:class-runtimeprofile = "ic_on_bg-profile.json"

# To avoid conflicts with the systemd template scheme added via bbappend in
# meta-agl-demo, package the standalone systemd unit separately.  This is not
# needed when meta-agl-flutter is used without meta-agl-demo, but that is not
# going to be the default usecase for most users, so this still allows them to
# build working agl-image-flutter images in the same build tree.
#
# This can be dropped if/when flutter-gallery is no longer packaged as a demo
# in meta-agl-demo.

#SYSTEMD_SERVICE:${PN}-init = "flutter-cluster-dashboard.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/flutter-cluster-dashboard.service ${D}${systemd_user_unitdir}/flutter-cluster-dashboard.service
    install -d ${D}${systemd_user_unitdir}/agl-session.target.wants
    ln -s ../flutter-cluster-dashboard.service ${D}${systemd_user_unitdir}/agl-session.target.wants/flutter-cluster-dashboard.service

    install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/default.json
}

PACKAGE_BEFORE_PN += "${PN}-init"

FILES:${PN} += "${datadir}"

FILES:${PN}-init = "${systemd_user_unitdir}"

RDEPENDS:${PN}-init = "${PN}"