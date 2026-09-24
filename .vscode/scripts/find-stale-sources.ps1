# Prints the full path of every .java file under $SrcDir that has no
# corresponding .class file under $OutDir yet, or whose .class is older than
# the source -- i.e. the set of files build.bat needs to (re)compile for an
# incremental build. One path per line, suitable for javac's "@file" syntax.
param(
    [Parameter(Mandatory=$true)][string]$SrcDir,
    [Parameter(Mandatory=$true)][string]$OutDir
)

$SrcDir = (Resolve-Path -LiteralPath $SrcDir).Path.TrimEnd('\')
$OutDir = (Resolve-Path -LiteralPath $OutDir).Path.TrimEnd('\')

Get-ChildItem -LiteralPath $SrcDir -Recurse -Filter *.java -File | ForEach-Object {
    $relative = $_.FullName.Substring($SrcDir.Length + 1)
    $relativeClass = [System.IO.Path]::ChangeExtension($relative, ".class")
    $classPath = Join-Path $OutDir $relativeClass

    if (-not (Test-Path -LiteralPath $classPath)) {
        $_.FullName
    } elseif ($_.LastWriteTime -gt (Get-Item -LiteralPath $classPath).LastWriteTime) {
        $_.FullName
    }
}
