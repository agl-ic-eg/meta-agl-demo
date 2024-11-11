# qtpdf has extensive dependencies (chromium)
# also there is a build issue as it requires deep in the 3rd-party tree cups-config
RDEPENDS:${PN}:remove = "qtpdf"
