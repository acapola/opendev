##
## Auto Generated makefile by CodeLite IDE
## any manual changes will be erased      
##
## Debug
ProjectName            :=ftdi
ConfigurationName      :=Debug
WorkspacePath          := "C:\Users\sebastien\Documents\dev\opendev\ftdi"
ProjectPath            := "C:\Users\sebastien\Documents\dev\opendev\ftdi"
IntermediateDirectory  :=./Debug
OutDir                 := $(IntermediateDirectory)
CurrentFileName        :=
CurrentFilePath        :=
CurrentFileFullPath    :=
User                   :=sebastien
Date                   :=10/27/14
CodeLitePath           :="C:\Program Files (x86)\CodeLite"
LinkerName             :=C:/MinGW-4.8.1/bin/g++.exe 
SharedObjectLinkerName :=C:/MinGW-4.8.1/bin/g++.exe -shared -fPIC
ObjectSuffix           :=.o
DependSuffix           :=.o.d
PreprocessSuffix       :=.i
DebugSwitch            :=-g 
IncludeSwitch          :=-I
LibrarySwitch          :=-l
OutputSwitch           :=-o 
LibraryPathSwitch      :=-L
PreprocessorSwitch     :=-D
SourceSwitch           :=-c 
OutputFile             :=$(IntermediateDirectory)/$(ProjectName)
Preprocessors          :=
ObjectSwitch           :=-o 
ArchiveOutputSwitch    := 
PreprocessOnlySwitch   :=-E
ObjectsFileList        :="ftdi.txt"
PCHCompileFlags        :=
MakeDirCommand         :=makedir
RcCmpOptions           := 
RcCompilerName         :=C:/MinGW-4.8.1/bin/windres.exe 
LinkOptions            :=  --enable-stdcall-fixup
IncludePath            := $(IncludeSwitch)C:/boost_1_56_0  $(IncludeSwitch). $(IncludeSwitch). 
IncludePCH             := 
RcIncludePath          := 
Libs                   := $(LibrarySwitch)ftd2xx 
ArLibs                 :=  "ftd2xx" 
LibPath                :=$(LibraryPathSwitch)C:/boost_1_56_0/stage/lib  $(LibraryPathSwitch)c:/ftdi 

##
## Common variables
## AR, CXX, CC, AS, CXXFLAGS and CFLAGS can be overriden using an environment variables
##
AR       := C:/MinGW-4.8.1/bin/ar.exe rcu
CXX      := C:/MinGW-4.8.1/bin/g++.exe 
CC       := C:/MinGW-4.8.1/bin/gcc.exe 
CXXFLAGS :=  -g -O0 -std=c++11 -Wall $(Preprocessors)
CFLAGS   :=  -g -O0 -Wall $(Preprocessors)
ASFLAGS  := 
AS       := C:/MinGW-4.8.1/bin/as.exe 


##
## User defined environment variables
##
CodeLiteDir:=C:\Program Files (x86)\CodeLite
UNIT_TEST_PP_SRC_DIR:=C:\UnitTest++-1.3
Objects0=$(IntermediateDirectory)/check.cpp$(ObjectSuffix) $(IntermediateDirectory)/safe_stream.cpp$(ObjectSuffix) $(IntermediateDirectory)/ftdi_raw_data_source.cpp$(ObjectSuffix) $(IntermediateDirectory)/crc32.cpp$(ObjectSuffix) 



Objects=$(Objects0) 

##
## Main Build Targets 
##
.PHONY: all clean PreBuild PrePreBuild PostBuild
all: $(OutputFile)

$(OutputFile): $(IntermediateDirectory)/.d $(Objects) 
	@$(MakeDirCommand) $(@D)
	@echo "" > $(IntermediateDirectory)/.d
	@echo $(Objects0)  > $(ObjectsFileList)
	$(LinkerName) $(OutputSwitch)$(OutputFile) @$(ObjectsFileList) $(LibPath) $(Libs) $(LinkOptions)

$(IntermediateDirectory)/.d:
	@$(MakeDirCommand) "./Debug"

PreBuild:


##
## Objects
##
$(IntermediateDirectory)/check.cpp$(ObjectSuffix): check.cpp $(IntermediateDirectory)/check.cpp$(DependSuffix)
	$(CXX) $(IncludePCH) $(SourceSwitch) "C:/Users/sebastien/Documents/dev/opendev/ftdi/check.cpp" $(CXXFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/check.cpp$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/check.cpp$(DependSuffix): check.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/check.cpp$(ObjectSuffix) -MF$(IntermediateDirectory)/check.cpp$(DependSuffix) -MM "check.cpp"

$(IntermediateDirectory)/check.cpp$(PreprocessSuffix): check.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/check.cpp$(PreprocessSuffix) "check.cpp"

$(IntermediateDirectory)/safe_stream.cpp$(ObjectSuffix): safe_stream.cpp $(IntermediateDirectory)/safe_stream.cpp$(DependSuffix)
	$(CXX) $(IncludePCH) $(SourceSwitch) "C:/Users/sebastien/Documents/dev/opendev/ftdi/safe_stream.cpp" $(CXXFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/safe_stream.cpp$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/safe_stream.cpp$(DependSuffix): safe_stream.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/safe_stream.cpp$(ObjectSuffix) -MF$(IntermediateDirectory)/safe_stream.cpp$(DependSuffix) -MM "safe_stream.cpp"

$(IntermediateDirectory)/safe_stream.cpp$(PreprocessSuffix): safe_stream.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/safe_stream.cpp$(PreprocessSuffix) "safe_stream.cpp"

$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(ObjectSuffix): ftdi_raw_data_source.cpp $(IntermediateDirectory)/ftdi_raw_data_source.cpp$(DependSuffix)
	$(CXX) $(IncludePCH) $(SourceSwitch) "C:/Users/sebastien/Documents/dev/opendev/ftdi/ftdi_raw_data_source.cpp" $(CXXFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(DependSuffix): ftdi_raw_data_source.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(ObjectSuffix) -MF$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(DependSuffix) -MM "ftdi_raw_data_source.cpp"

$(IntermediateDirectory)/ftdi_raw_data_source.cpp$(PreprocessSuffix): ftdi_raw_data_source.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/ftdi_raw_data_source.cpp$(PreprocessSuffix) "ftdi_raw_data_source.cpp"

$(IntermediateDirectory)/crc32.cpp$(ObjectSuffix): crc32.cpp $(IntermediateDirectory)/crc32.cpp$(DependSuffix)
	$(CXX) $(IncludePCH) $(SourceSwitch) "C:/Users/sebastien/Documents/dev/opendev/ftdi/crc32.cpp" $(CXXFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/crc32.cpp$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/crc32.cpp$(DependSuffix): crc32.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/crc32.cpp$(ObjectSuffix) -MF$(IntermediateDirectory)/crc32.cpp$(DependSuffix) -MM "crc32.cpp"

$(IntermediateDirectory)/crc32.cpp$(PreprocessSuffix): crc32.cpp
	@$(CXX) $(CXXFLAGS) $(IncludePCH) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/crc32.cpp$(PreprocessSuffix) "crc32.cpp"


-include $(IntermediateDirectory)/*$(DependSuffix)
##
## Clean
##
clean:
	$(RM) ./Debug/*$(ObjectSuffix)
	$(RM) ./Debug/*$(DependSuffix)
	$(RM) $(OutputFile)
	$(RM) $(OutputFile).exe
	$(RM) ".build-debug/ftdi"


