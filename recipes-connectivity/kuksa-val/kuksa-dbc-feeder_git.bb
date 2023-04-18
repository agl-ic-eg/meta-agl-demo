SUMMARY = "DBC feeder for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=175792518e4ac015ab6696d16c4f607e"

DEPENDS = "python3-setuptools-git-versioning-native"

PV = "0.1.2+git${SRCPV}"

SRC_URI = "git://github.com/eclipse/kuksa.val.feeders.git;protocol=https;branch=main \
           file://0001-dbc2val-add-installation-mechanism.patch \
           file://0002-dbc2val-usability-improvements.patch \
           file://0003-dbc2val-fix-token-file-configuration-option.patch \
           file://config.ini \
           file://dbc_feeder.json.token \
           file://agl-vcar.dbc \
           file://kuksa-dbc-feeder.service \
"
SRCREV = "d5fe991706bd8fc6d92ffbbaa838a380067f201f"

S = "${WORKDIR}/git"

inherit setuptools3 systemd

SETUPTOOLS_SETUP_PATH = "${S}/dbc2val"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -d ${D}${sysconfdir}/kuksa-dbc-feeder
    install -m 0644 ${WORKDIR}/config.ini ${D}${sysconfdir}/kuksa-dbc-feeder/
    # Token should ideally not be readable by other users.
    # The potential for running the feeder as non-root will take some
    # investigation.
    install -m 0600 ${WORKDIR}/dbc_feeder.json.token ${D}${sysconfdir}/kuksa-dbc-feeder/
    install -m 0644 ${WORKDIR}/agl-vcar.dbc ${D}${sysconfdir}/kuksa-dbc-feeder/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-dbc-feeder.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += " \
    bash \
    python3-pyserial \
    python3-cantools \
    python3-can \
    python3-can-j1939 \
    python3-pyyaml \
    python3-py-expression-eval \
    kuksa-client \
    can-dev-helper \
"
