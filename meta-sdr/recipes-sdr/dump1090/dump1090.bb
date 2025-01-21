SUMMARY = "Dump1090 is a simple Mode S decoder for RTLSDR devices"
HOMEPAGE = "https://github.com/antirez/dump1090"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://README.md;beginline=283;endline=284;md5=2037b2ec964aa1cd90ebae1d44936390"

DEPENDS = "rtl-sdr"
RDEPENDS:${PN} = "rtl-sdr"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "dump1090.service"

SRC_URI = " \
  git://github.com/antirez/dump1090.git;protocol=https;branch=master \
  file://dump1090.service \
"
SRCREV = "0c3bb23eb447d4ae47c7013346fa6fa97482bb1d"

S = "${WORKDIR}/git"

inherit pkgconfig

do_install() {
  install -d ${D}${bindir}
  install -m 0755 ${S}/dump1090 ${D}${bindir}/

  install -d ${D}${libdir}
  install -d ${D}${libdir}/dump1090
  install -m 0755 ${S}/gmap.html ${D}${libdir}/dump1090/gmap.html

  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/dump1090.service ${D}/${systemd_unitdir}/system
}

FILES:${PN} = " \
  ${bindir}/dump1090 \
  ${libdir}/dump1090/gmap.html \
  ${systemd_unitdir}/system/dump1090.service \
"
