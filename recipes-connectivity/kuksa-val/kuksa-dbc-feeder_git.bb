SUMMARY = "DBC feeder for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

DEPENDS = "python3-setuptools-git-versioning-native"

require kuksa-val.inc

SRC_URI += "file://0001-dbc2val-add-installation-mechanism.patch \
            file://0002-dbc2val-usability-improvements.patch \
            file://config.ini \
            file://dbc_feeder.json.token \
            file://mapping.yml \
            file://agl-vcar.dbc \
            file://kuksa-dbc-feeder.service \
"

inherit setuptools3 systemd

DISTUTILS_SETUP_PATH = "${S}/kuksa_feeders"

# This is a bit of a workaround as the sed in distutils.bbclass
# will remove the -S already present in the script otherwise,
# breaking it.
DISTUTILS_PYTHON = "-S\ python3"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -d ${D}${sysconfdir}/kuksa-dbc-feeder
    install -m 0644 ${WORKDIR}/config.ini ${D}${sysconfdir}/kuksa-dbc-feeder/
    # Token should ideally not be readable by other users.
    # The potential for running the feeder as non-root will take some
    # investigation.
    install -m 0600 ${WORKDIR}/dbc_feeder.json.token ${D}${sysconfdir}/kuksa-dbc-feeder/
    install -m 0644 ${WORKDIR}/mapping.yml ${D}${sysconfdir}/kuksa-dbc-feeder/
    install -m 0644 ${WORKDIR}/agl-vcar.dbc ${D}${sysconfdir}/kuksa-dbc-feeder/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-dbc-feeder.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += " \
    python3-pyserial \
    python3-cantools \
    python3-can \
    python3-can-j1939 \
    python3-pyyaml \
    python3-py-expression-eval \
    kuksa-viss-client \
    can-dev-helper \
"
