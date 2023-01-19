SUMMARY = "Flutter Instrument Cluster "
DESCRIPTION = "An instrument cluster app written in dart for the flutter runtime"
AUTHOR = "Aakash Solanki"
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0c52b0e4b5f0dbf57ea7d44bebb2e29d"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster;protocol=https;branch=${AGL_BRANCH} \
    file://flutter-cluster-dashboard.service \
    file://flutter_cluster_dashboard_on_bg-debug.json \
    file://flutter_cluster_dashboard_on_bg-profile.json \
    file://flutter_cluster_dashboard_on_bg-release.json \
    file://flutter-cluster-dashboard.yaml \
    file://flutter-cluster-dashboard.yaml.demo \
"

PV = "1.0+git${SRCPV}"
SRCREV = "c605ab037edcb35a7aa365fa6abe7fd6289d8a19"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_cluster_dashboard"

FLUTTER_APPLICATION_INSTALL_PREFIX = "/flutter"

OPENROUTE_API_KEY ??= "YOU_NEED_TO_SET_IT_IN_LOCAL_CONF"

inherit flutter-app update-alternatives

CLUSTER_DEMO_VISS_HOSTNAME ??= "192.168.10.2"

APP_CONFIG = "flutter_cluster_dashboard_on_bg-release.json"
APP_CONFIG:class-runtimedebug = "flutter_cluster_dashboard_on_bg-debug.json"
APP_CONFIG:class-runtimeprofile = "flutter_cluster_dashboard_on_bg-profile.json"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/flutter-cluster-dashboard.service ${D}${systemd_user_unitdir}/flutter-cluster-dashboard.service
    install -d ${D}${systemd_user_unitdir}/agl-session.target.wants
    ln -s ../flutter-cluster-dashboard.service ${D}${systemd_user_unitdir}/agl-session.target.wants/flutter-cluster-dashboard.service

    install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/default.json

    install -d ${D}${sysconfdir}/xdg/AGL
    install -m 0644 ${WORKDIR}/flutter-cluster-dashboard.yaml \
        ${D}${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.default
    install -m 0644 ${WORKDIR}/flutter-cluster-dashboard.yaml.demo ${D}${sysconfdir}/xdg/AGL/
    sed -i "s/^hostname: .*/hostname: ${CLUSTER_DEMO_VISS_HOSTNAME}/" ${D}${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.demo

    install -m 0755 -d ${D}${sysconfdir}/default/ 
    echo 'OPENROUTE_API_KEY:${OPENROUTE_API_KEY}' >> ${D}${sysconfdir}/default/openroutekey
}

ALTERNATIVE_LINK_NAME[flutter-cluster-dashboard.yaml] = "${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml"

FILES:${PN} += "${datadir} ${systemd_user_unitdir} ${sysconfdir}/default/"

PACKAGE_BEFORE_PN += "${PN}-conf ${PN}-conf-demo"

FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "flutter-cluster-dashboard.yaml"
RCONFLICTS:${PN}-conf = "${PN}-conf-demo"
ALTERNATIVE:${PN}-conf = "flutter-cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.default"

FILES:${PN}-conf-demo += "${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.demo"
RDEPENDS:${PN}-conf-demo = "${PN}"
RPROVIDES:${PN}-conf-demo = "flutter-cluster-dashboard.yaml"
RCONFLICTS:${PN}-conf-demo = "${PN}-conf"
ALTERNATIVE:${PN}-conf-demo = "flutter-cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf-demo = "${sysconfdir}/xdg/AGL/flutter-cluster-dashboard.yaml.demo"
