SUMMARY = "Flutter Instrument Cluster "
DESCRIPTION = "An instrument cluster app written in dart for the flutter runtime"
AUTHOR = "Aakash Solanki"
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster"

SECTION = "graphics"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0c52b0e4b5f0dbf57ea7d44bebb2e29d"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/flutter-instrument-cluster;protocol=https;branch=${AGL_BRANCH} \
    file://flutter-cluster-dashboard.service \
    file://flutter_cluster_dashboard_on_bg.json \
    file://cluster-dashboard.yaml \
    file://cluster-dashboard.yaml.demo \
    file://cluster-dashboard.yaml.gateway-demo \
    file://cluster-dashboard.yaml.kvm-demo \
    file://cluster-dashboard.token \
    file://kvm.conf \
"

PV = "1.0+git${SRCPV}"
SRCREV = "5bd594ae8ad92e8e74966c4ad87df83ec8088c2d"

S = "${WORKDIR}/git"

PUBSPEC_APPNAME = "flutter_cluster_dashboard"

inherit flutter-app update-alternatives systemd

APP_CONFIG = "flutter_cluster_dashboard_on_bg.json"

SYSTEMD_SERVICE:${PN} = "flutter-cluster-dashboard.service"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service

    install -D -m 0644 ${WORKDIR}/kvm.conf ${D}${systemd_system_unitdir}/${BPN}.service.d/kvm.conf

    install -D -m 0644 ${WORKDIR}/${APP_CONFIG} ${D}${datadir}/flutter/${BPN}.json

    install -d ${D}${sysconfdir}/xdg/AGL/cluster-dashboard
    install -m 0644 ${WORKDIR}/cluster-dashboard.yaml ${D}${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.default
    install -m 0644 ${WORKDIR}/cluster-dashboard.yaml.demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/cluster-dashboard.yaml.gateway-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/cluster-dashboard.yaml.kvm-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/cluster-dashboard.token ${D}${sysconfdir}/xdg/AGL/cluster-dashboard/
}

ALTERNATIVE_LINK_NAME[cluster-dashboard.yaml] = "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml"

FILES:${PN} += "${datadir} ${sysconfdir}/xdg/AGL"

RDEPENDS:${PN} += "flutter-auto agl-flutter-env liberation-fonts"

PACKAGE_BEFORE_PN += "${PN}-conf"
FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "cluster-dashboard.yaml"
ALTERNATIVE:${PN}-conf = "cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.default"

PACKAGE_BEFORE_PN += "${PN}-conf-demo"
FILES:${PN}-conf-demo += "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.demo"
RDEPENDS:${PN}-conf-demo = "${PN}"
RPROVIDES:${PN}-conf-demo = "cluster-dashboard.yaml"
ALTERNATIVE:${PN}-conf-demo = "cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf-demo = "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.demo"

PACKAGE_BEFORE_PN += "${PN}-conf-gateway-demo"
FILES:${PN}-conf-gateway-demo += "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.gateway-demo"
RDEPENDS:${PN}-conf-gateway-demo = "${PN}"
RPROVIDES:${PN}-conf-gateway-demo = "cluster-dashboard.yaml"
ALTERNATIVE:${PN}-conf-gateway-demo = "cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf-gateway-demo = "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.gateway-demo"

# systemd override to add network-online.target dependency for KVM setups
PACKAGE_BEFORE_PN += "${PN}-conf-kvm"
FILES:${PN}-conf-kvm += "${systemd_system_unitdir}/flutter-cluster-dashboard.service.d/kvm.conf"
RDEPENDS:${PN}-conf-kvm = "${PN}"

PACKAGE_BEFORE_PN += "${PN}-conf-kvm-demo"
FILES:${PN}-conf-kvm-demo += "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.kvm-demo"
RDEPENDS:${PN}-conf-kvm-demo = "${PN} ${PN}-conf-kvm"
RPROVIDES:${PN}-conf-kvm-demo = "cluster-dashboard.yaml"
ALTERNATIVE:${PN}-conf-kvm-demo = "cluster-dashboard.yaml"
ALTERNATIVE_TARGET_${PN}-conf-kvm-demo = "${sysconfdir}/xdg/AGL/cluster-dashboard.yaml.kvm-demo"
