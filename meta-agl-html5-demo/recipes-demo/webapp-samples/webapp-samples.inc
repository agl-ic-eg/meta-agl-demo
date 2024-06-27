HOMEPAGE    = "https://github.com/AGL-web-applications/webapp-samples"
SECTION     = "apps"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://github.com/AGL-web-applications/webapp-samples.git;protocol=https;branch=master \
  file://${APPINFO_FILE} \
"
SRCREV = "b24a62cc524bdec98a61acc03fc53cbab24c1571"

WAM_APPLICATIONS_DIR="${libdir}/wam_apps"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  install ${WORKDIR}/${APPINFO_FILE} ${D}${WAM_APPLICATIONS_DIR}/${PN}/appinfo.json
}

FILES:${PN} = " \
  ${WAM_APPLICATIONS_DIR}/${PN} \
"
