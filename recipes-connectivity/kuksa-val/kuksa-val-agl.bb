SUMMARY = "AGL demo signal overlay for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://kuksa-val.env"

inherit allarch update-alternatives

do_install() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/kuksa-val.env ${D}${sysconfdir}/default/kuksa-val.agl
}

FILES:${PN} += "${datadir}/kuksa-val/overlays"

ALTERNATIVE:${PN} = "kuksa-val-env"
ALTERNATIVE_LINK_NAME[kuksa-val-env] = "${sysconfdir}/default/kuksa-val"
ALTERNATIVE_TARGET[kuksa-val-env] = "${sysconfdir}/default/kuksa-val.agl"

RDEPENDS:${PN} += "kuksa-val vss-agl"
