SUMMARY     = "AGL HTML5 Homescreen"
HOMEPAGE    = "https://git.automotivelinux.org/apps/html5-homescreen/"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://gerrit.automotivelinux.org/gerrit/apps/html5-homescreen;protocol=https;branch=master \
  file://homescreen.service \
"
SRCREV = "f3cecb6e79d7fcac14535108c06cb5a922159d78"

inherit python3native

DEPENDS = "nodejs-native"

do_compile[network] = "1"
do_compile() {
  cd ${S}
  rm -rf package node_modules package-lock.json
  npm install
  npm run build
}

WAM_APPLICATIONS_DIR = "${libdir}/wam_apps"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
  install -d ${D}${systemd_user_unitdir}/agl-session.target.wants
  install -m 0644 ${WORKDIR}/homescreen.service ${D}${systemd_user_unitdir}/homescreen.service
  ln -s ../homescreen.service ${D}${systemd_user_unitdir}/agl-session.target.wants/homescreen.service
}

FILES:${PN} = " \
  ${WAM_APPLICATIONS_DIR}/${PN} \
  ${systemd_user_unitdir} \
"

RCONFLICTS:${PN} = "homescreen"
RDEPENDS:${PN} = "applaunchd html5-background"
