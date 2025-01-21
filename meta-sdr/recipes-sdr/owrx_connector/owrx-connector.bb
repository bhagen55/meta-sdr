SUMMARY = "Direct conection layer for openwebrx ."

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
  git://github.com/luarvique/owrx_connector.git;protocol=https;branch=master \
"

# Tag 0.6.5
SRCREV = "870285269143048f850151346980942a12ccf24b"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "csdr rtl-sdr"
