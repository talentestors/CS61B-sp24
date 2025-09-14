#!/usr/bin/env bash
set -euf -o pipefail

# https://stackoverflow.com/a/1371283
# Get the current folder name
result=${PWD##*/}          # to assign to a variable
result=${result:-/}        # to correct for the case where PWD=/

# https://stackoverflow.com/a/229606
# See if we're in a student folder and exit if so
if [[ $result == *"sp24"* ]]; then
  printf "
   ____________________________________
  / Oops! Make sure you run the script \\
  \ outside of your student folder.    /
   ------------------------------------
          \   ^__^
           \  (oo)\_______
              (__)\       )\/\\
                  ||----w |
                  ||     ||
  "
  echo
  exit 0
fi

git submodule add https://github.com/Berkeley-CS61B/git-exercise-sp24.git
git submodule update --init --recursive
