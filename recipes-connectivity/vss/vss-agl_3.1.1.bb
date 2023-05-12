SUMMARY = "Vehicle Signal Specification with AGL overlays"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS = "vss-tools-native"

inherit allarch update-alternatives

require vss.inc

SRC_URI += "file://agl_vss_overlay.vspec"

# Since we're not relying on the simple upstream repo Makefile, use
# best practices and output into a separate directory.
B = "${WORKDIR}/build"

do_configure[noexec] = "1"

VSPEC2JSON_OPTS = "-e dbc -o ${WORKDIR}/agl_vss_overlay.vspec --no-uuid --json-pretty"

do_compile() {
    vspec2json.py -I ${S}/spec ${VSPEC2JSON_OPTS} -u ${S}/spec/units.yaml ${S}/spec/VehicleSignalSpecification.vspec vss_rel_${PV}-agl.json
}

do_install() {
    install -d ${D}${datadir}/vss
    install -m 0644 vss_rel_${PV}-agl.json ${D}${datadir}/vss/
}

ALTERNATIVE_LINK_NAME[vss.json] = "${datadir}/vss/vss.json"

ALTERNATIVE_PRIORITY = "20"
ALTERNATIVE:${PN} = "vss.json"
ALTERNATIVE_TARGET_${PN} = "${datadir}/vss/vss_rel_${PV}-agl.json"

FILES:${PN} += "${datadir}/vss/"
