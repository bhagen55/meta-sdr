SUMMARY = "A simple DSP library and command-line tool for Software Defined Radio."

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE-GPL;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
  git://github.com/luarvique/csdr.git;protocol=https;branch=master \
"

# Tag 0.18.27
SRCREV = "6738f87987ac2d965ff7bc28f677465cc5805b31"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "fftw libsamplerate0"

# TODO: split out library and executable
