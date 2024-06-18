SUMMARY = "CAN provider for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse-kuksa"
BUGTRACKER = "https://github.com/eclips-kuksa/kuksa-can-provider/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

DEPENDS = "python3-setuptools-git-versioning-native"

PV = "0.4.3+git${SRCPV}"

SRC_URI = "git://github.com/eclipse-kuksa/kuksa-can-provider.git;protocol=https;branch=main \
           file://0001-dbc2val-add-installation-mechanism.patch \
           file://0002-dbc2val-usability-improvements.patch \
           file://0003-dbc2val-fix-token-file-configuration-option.patch \
           file://0004-Enable-val2dbc-for-sensor-values.patch \
           file://config.ini \
           file://can-provider.token \
           file://agl-vcar.dbc \
           file://dbc_default_values.json \
           file://kuksa-can-provider.service \
	   file://kuksa-can-provider.default \
           "
SRCREV = "d5fdcfa7574ce69059b6f449629571db0982c1cf"

S = "${WORKDIR}/git"

inherit setuptools3 systemd update-alternatives

#SETUPTOOLS_SETUP_PATH = "${S}/dbc2val"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_install:append() {
    install -d ${D}${sysconfdir}/kuksa-can-provider
    install -m 0644 ${WORKDIR}/config.ini ${D}${sysconfdir}/kuksa-can-provider/
    # Token should ideally not be readable by other users.
    # The potential for running the feeder as non-root will take some
    # investigation.
    install -m 0600 ${WORKDIR}/can-provider.token ${D}${sysconfdir}/kuksa-can-provider/
    install -m 0644 ${WORKDIR}/agl-vcar.dbc ${D}${sysconfdir}/kuksa-can-provider/
    install -m 0644 ${WORKDIR}/dbc_default_values.json ${D}${sysconfdir}/kuksa-can-provider/
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/kuksa-can-provider.default ${D}${sysconfdir}/default/
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-can-provider.service ${D}${systemd_system_unitdir}
    fi
}

ALTERNATIVE_LINK_NAME[kuksa-can-provider.env] = "${sysconfdir}/default/kuksa-can-provider"

ALTERNATIVE_TARGET_${PN} = "${sysconfdir}/default/kuksa-can-provider.default"
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
