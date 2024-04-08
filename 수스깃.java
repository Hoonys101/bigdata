< 수스 깃 >

1. Git
	(01) (Git-2.44.0-64-bit.exe) 다운로드 및 인스톨 후.. Git bash 열기 
		https://www.git-scm.com/

		<1> 복사 
		    - Ctrl + Insert 
			- 텍스트 드레그 
		<2> 붙여넣기
			Shift + Insert
			
	(02) 버젼 체킹
		$ git --version

	(03) 깃 설정 
		$ git config --global user.name "khs4git3-test"
		$ git config --global user.email "khs4git3@gmail.com"

		Tip) 설정확인 
		$ git config --list 

	(04) 플젝 디렉토리 생성 
		1) D:\SOO\Git\pj_git 생성 
		2) pj_git -> 우클릭 -> 'Open Git Bash here'
		
		Tip) 디렉토리 이동 예 
			$ cd 
			$ cd /d/SOO/Git/pj_git

	(05) Repository 생성
		$ git init

		결과) pj_git/.git 숨김폴더로 생성됨
	
	(06) 새 파일 추가 
		index.html 생성/추가

		$ git status 
		//상태확인 -> Untracked files: index.html (빨강색)

	(07) 스테이징(1) 
		$ git add index.html

		$ git status
			//상태확인 -> Changes to be committed: new file: index.html (녹색)

	(08) 스테이징(N)
		- css/index.css '생성'
		- index.html '수정' 
			<link rel="stylesheet" href="css/index.css">
		- README.md '생성'

		$ git status  //빨강색( 1개수정 + 2개의 생성 리스트 )
		$ git add --all //모든 파일들 스테이징
		$ git status
			//new file:   README.md
			//new file:   css/index.css
			//new file:   index.html

	(09) 커밋 
		$ git commit -m "첫번째 커밋"
			//create mode 100644 README.md
			//create mode 100644 css/index.css
			//create mode 100644 index.html

	(10) 바로커밋 ( 스테이징 없이.. ) //권장되지 않는 방법 
		- index.html 수정 ( '<p>사랑은 달콤하고 엄마처럼 다정하고 잠처럼 편하고 꿈처럼 행복한거야</p>' )

		$ git status --short //심플상태확인 

		#참고) 결과표시
			?? : 'Untracked files' 
			A : 'Files added to stage'
			M : 'Modified files'
			D : 'Deleted files'

		$ git commit -a -m "index.html에 <p>가 추가되어짐"
		$ git status --short

		#참고) -a 옵션: all의 약자 ( commit all changed files )

	(11) 커밋 로그 확인
		$ git log

	(12) 도움 명령어 
		1) 특정 명령어에 대한 옵션 
			$ git commit -help
			참고) git commit --help //매뉴얼 페이지 열기 

		2) 모든 명령어 확인
			$ git help --all
			참고) Shift + G //끝으로 이동 
				q //나가기

	(13) branch 생성1
		$ git branch b1-test //생성
		$ git branch //확인 
			//b1-test
			//* master : 현재 작업자 

		$ git checkout b1-test
		$ git branch //확인
			//* b1-test : 현재 작업자
			//master

	(14) 브랜치 계정('b1-test')에서 일(work) 해보자
		1) pj_git/imgs/daum.png 다운로드/추가
		2) index.html 수정 ( '<div><img src="imgs/daum.png"></div>' )

			$ git status //긴 설명 
			$ git status --short //짧은 설명
				//M index.html
				//?? imgs/

			$ git add --all //모든 파일들 스테이징
			$ git status --short
				//A  imgs/daum.png
				//M  index.html

			$ git commit -m "다음 이미지(daum.png)를 추가하고 index.html에 적용함"
			$ dir //확인 ( README.md  css  imgs  index.html )
			$ git log
		3) index.html 열어서 확인 

	(15) 마스터 계정('master')으로 이동 
		$ git checkout master //마스터로 이동
		
		$ dir //확인 ( README.md  css  index.html )
		
		참고) index.html 열어서 확인 

	(16) branch 생성2 ('b2-test')
		1) 방법1
			$ git branch b2-test //생성
			$ git branch //확인 
				//b1-test
				//b2-test
				//* master
			$ git checkout b2-test //작업 브랜치(*)로 이동
			$ git branch //확인
				//b1-test
				//* b2-test
				//master

		2) 방법2 ( 더 간단하니 '-b' 옵션 '추천' )  
			$ git checkout -b b3-test  //생성+이동 
			$ git branch //확인
				//b1-test
				//b2-test
				//* b3-test
				//master

	(17) 브랜치 계정('b2-test')에서 일(work) 해보자
		$ git checkout b2-test  
		$ git branch 
		
		- index.html 수정 ( '<p>두번째브랜치(b2-test)에서 추가한 구문</p>' ) 

		$ git status //빨강색
		$ git add index.html //스테이징 
		$ git status --short //확인 ( M  index.html )
		$ git commit -m "두번째브랜치(b2-test)에서 추가했음"
		$ git log

	(18) 머쥐(Merge) 
		$ git checkout master //마스터로 이동 
		$ git branch //확인 
			//* master
			cf) index.html 열어서 확인1

		$ git merge b2-test //현재 계정(master) + 브랜치계정(b2-test)
			cf) index.html 열어서 확인2

		$ git branch -d b2-test //브랜치계정(b2-test) 삭제 
		$ git branch //확인 

		$ git branch -d b3-test //브랜치계정(b3-test) 삭제 
		$ git branch //확인
			//b1-test
			//* master

	(19) 머쥐 충돌 (Merge Conflict)
		$ git checkout b1-test

		1) pj_git/imgs/google.png 다운로드/추가
		2) index.html 수정 ( '<div><img src="imgs/google.png"></div>' )

		$ git status  //빨강색( index.html  imgs/google.png ) 확인 
		$ git add --all //스테이징
		$ git status //녹색으로 변경 

		$ git commit -m "b1-test 브랜치에서 구글 이미지 추가 했음" //커밋
		$ git log //히스토리 확인 
		q

		$ git checkout master 
		$ git merge b1-test //병합시도 -> 실패!

		$ git status //실패이유? 버젼충돌 ( both modified:   index.html )

	(20) 머쥐 충돌 해결 ( 위 19번의 머징 계속 진행 중.. )
		$ git branch
		   //b1-test
		   //* master

		- index.html 어떤 부분이든 수정 
		   예1) '<div><img src="imgs/google.png" alt="Good morning Google"></div>'
		   예2) 아래 삭제 
			   <<<<<<< HEAD 
			   =======
			   >>>>>>> b1-test

		$ git status //빨간색( index.html ) 
		$ git add index.html //스테이징
		$ git status //녹색
			//new file:   imgs/daum.png
			//new file:   imgs/google.png
			//modified:   index.html

		$ git commit -m "commit을 이용해서 b1-test와 병합 완료(Conflict 해결)"
		$ git branch
			// b1-test
			//* master

		- index.html 확인 //지금까지 작업한 모든(master + b1 + b2)내용이 병합됨 ( 최종적인 목표 달성 )

		//$ git merge b1-test //Already up to date ( 필요없음 )


		$ git checkout b1-test
		$ git branch
			//* b1-test
			//master
		
		- index.html 확인 //지금까지 작업한 내용(master + b1)내용이 병합됨 
		                 //( 주의: b2의 작업내용은 병합X ) 

		$ git checkout master
		$ git branch -d b1-test //마지막 브랜치 삭제
		$ git branch 
			//* master 
		 
	(21) 대화 시나리오 
		- b1 : master님이랑 작업 중이니 기분이 좋아요 ㅎㅎ 
		- master : 나도 b1와 작업할 수 있어서 좋아 ㅎㅎ 
		- b2 : 방가요 master님. 전 새로운 브랜치예요. 이젠 저랑 작업해요 
		- master : 그래요 b2 
		- b2 : 작업이 끝났으니 merge 부탁해요 
		- master : OK 병합완료! 

		- b1 : 헉.. 나도 작업 끝냈는데.. mater님의 작업 내용이 나도 모르게 변했네 ㅠㅠ ( 화남! )
		- master : 잠시만요 merge 시도해 볼께요. 헉.. 실패!
		- b1 : 당신이 나랑 작업중이었던 mater의 내용인가요? 아님 병합된 다른 branch의 내용인가요? 증명해 보이세요
		- master : 음.. 내가 commit해서 전에 당신과 작업중이었던 나란 걸 증명할께요. 
		- b1 : ㅋ병합 성공!
		

2. GitHub
	(01) 깃허브 계정
		1) https://github.com/

		2) Create an account
			- Email: khs4git3@gmail.com
			- Pwd: 
			- Name: khs4git3-test
			- y 

	(02) Repository 생성 
		1) 우측상단 '+' -> New repository
		2) Repository name: 'pj-git-repository'
				//중요) Add a README file: '체크 하지 말것!!'
		3) Create repository 버튼
		4) Quick setup 하위의 URL 복사
			https://github.com/khs4git3-test/pj-git-repository.git

	(03) '로컬'과 '원격' 연결 / 푸쉬
		1) 로컬과 연결 ( pj_git폴더에 우클릭 -> Git Bash Here ) 
			$ git remote add origin https://github.com/khs4git3-test/pj-git-repository.git

		2) 확인 
			$ git remote -v
			//origin  https://github.com/khs4git3-test/pj-git-repository.git (fetch)
			//origin  https://github.com/khs4git3-test/pj-git-repository.git (push)

		3) 푸쉬 (로컬 -> 원격)
			$ git push --set-upstream origin master

			//참고) 처음 열결시.. 인증확인창이 1회 뜸 
				<1> 팝업 확인 
					'Sign in your browser' 클릭 

				<2> 웹브라우져 확인
					'Authorize git-ecosystem' 클릭 
					-> 'Authorize GitCredentialManager'의 'Authentication Succeeded' 메세지 확인 	

		4) 새로고침
			<1> master 브랜치가 생겨서 default로 지정 됨
			<2> 원격저장소에 로컬파일들 Updating 확인 됨

	(04) 깃허브에서 편집을 해 보자
		1) 하단의 README.md 우측에 '연필'아이콘
		2) 임의의 내용 추가 ( '깃허브에서 추가한 내용' )
		3) 우측의 'Commit changes' 버튼 클릭 
			- 커밋메세지추가 ( '깃허브에서의 커밋 메세지' ) 
			- 'Commit changes' 버튼 클릭
	    4) 결과 
			- 깃허브의 README.md에서는 '깃허브에서 추가한 내용' 보임 
			- 로컬깃의 README.md에서는 '깃허브에서 추가한 내용' 보이지 않음
			
	(05) 풀(Pull) : '원격->로컬'
		1) Fetch 
			$ git fetch origin  //원격 내용을 가져옴
			$ git status
			$ git log origin/master  //최신 시간 순서로 커밋메세지 확인
				q //나가기
			$ git diff origin/master  //로컬마스터와 원격(origin)마스터의 간의 차이점들 확인
				q //나가기

		2) Merge
			$ git merge origin/master //원격마스터와 병합(가져옴)
			//로컬깃의 README.md에서는 '깃허브에서 추가한 내용' 보임
			$ git status
			//Your branch is up to date with 'origin/master'


			cf) 한방에 Pull(Fetch + Merge) 하기 //좋음!
				$ git pull origin

	(06) 푸쉬(Push) : '로컬->원격'
		1) 쏘스 변경 
			로컬의 index.html에서 .. 
			<1> 'git.png'다운로드  
			<2> <div><img src="imgs/git.png" alt="Hello Git"></div>

		2) Push
			$ git status  //빨간것이 2개( index.html, imgs/git.png ) 확인
			$ git add --all //모든 파일들 스테이징
			$ git commit -a -m "index.html에 git.png 적용 업데이트" //커밋 
			$ git status //빨간것들 모두 사라짐

			$ git push origin //원격의 2개(index.html, imgs/git.png) 반영 확인

			//원격에서 추가/변경 내용 확인 완료!

	(07) 깃허브에서 branch 생성
		1) 브랜치('b1-test-remote') 생성
			master 드롭박스 -> Find or create a branch.. 
			-> b1-test-remote 붙여넣기 -> Create branch~~ 클릭 
			//생성 후 이동 확인 

		2) index.html 수정
			<1> b1-test-remote 상태에서 index.html 클릭
			<2> '연필' 아이콘 클릭 후..   
				<div>이 메세지는 b1-test-remote 상태에서 추가한 것 임</div>
			<3> Preview 탭 
			    1> 색깔로 표시된 수정부분 확인  
				2> Commit changes 
					- 제목: index.html 수정 by b1-test-remote
					- 내용: b1-test-remote 상태에서 index.html 수정함
				3> Commit directly to the b1-test-remote branch (기본선택) 확인
				4> 'Commit changes' 버튼 클릭

	(08) 깃허브의 브랜치('b1-test-remote')를 로컬 브랜치로 당겨오기(Pull) 
		$ git pull 
		$ git status 

		$ git branch //로컬브랜치만 
			//* master 밖에 안보임
		$ git branch -a //로컬과 원격의 모든 브랜치 
			//* master
			//remotes/origin/b1-test-remote
			//remotes/origin/master
		$ git branch -r //원격브랜치만
			//remotes/origin/b1-test-remote
			//remotes/origin/master

		$ git checkout b1-test-remote // b1-test-remote 로 이동 
			//로컬 index.html에 '<div>이 메세지는 b1-test-remote 상태에서 추가한 것 임</div>'반영 확인 
		$ git branch 	
			//* b1-test-remote //추가됨( 원격브랜치 -> 로컬브랜치 복사 )
		$ git branch -a
			//* b1-test-remote //새로 생성 
			//master
			//remotes/origin/b1-test-remote
			//remotes/origin/master
		$ git pull
			//Already up to date 확인


	(09) 로컬 브랜치의 작업을 깃허브에 푸쉬(Push) + 원격(Pull request) //작업자의 핵심 루틴 
		1) 로컬 //푸쉬(Push): '팀원의 일'
			$ git checkout -b b4-test //생성 + 이동
			$ git branch
				//  b1-test-remote
				//* b4-test
				//  master

			- README.md ( '로컬브랜치 b4-test에서 추가한 내용' )

			$ git add README.md 
			$ git status 
				//modified:   README.md
			$ git commit -m "로컬브랜치 b4-test에서 커밋 함"
				
			$ git push origin b4-test //원격의 branch에 'b4-test'추가 확인 
			//원격창에서 'b4-test'를 선택하면.. 변경내용이 확인되지만.. 우리가 원하는 건 'master에서 반영'되길 바람

		2) 원격 //풀리퀘스트 (Pull request): '팀장의 일'
			<1> 'master' 브랜치 선택 
			<2> '3 branches' 클릭 
			<3> 'b4-test'의 휴지통 옆의 '...' 클릭 ->  'New pull request'클릭
			<4> Write탭 -> '로컬의 b4-test브랜치에서 작업한 README.md 수정 건에 대해 Pull요청' 
				-> 'Create pull request' 버튼
			<5> Conversation탭 -> 해당건 -> 'Merge pull request' 버튼 클릭
			<6> Confirm Merge 버튼
				//Pull request successfully merged and closed
				//You’re all set—the b4-test branch can be safely deleted.
			<7> 왼쪽 상단의 '<> Code'탭 클릭
				- 'master' 브랜치 확인
				- README.md 갱신 확인 //로컬브랜치 b4-test에서 추가한 내용
			<8> b4-test 삭제 
				- master -> 'View all branches'클릭 -> b4-test라인의 휴지통 아이콘 
				   -> 새로고침 -> 삭제 확인
				
			   참고) Active는 최근 3개월간 활성된 브랜치를 출력함


		//작업팀을 구성 후.. 테스팅 필요!! 
		/*
		3) Pull ( 원격 master -> 로컬 mater ) : '팀장/팀원의 일'? //시도해 보자!  
			$ git checkout master
			$ git pull origin //성공

				cf) 의문점
			    - 팀원도 master가 존재하는 가? 
			    - 팀원의 master는 원격의 master와 같은 권한인가? 
		*/
			
	(10) 깃허브의 작업 흐름 
		1) 새 로컬브랜치 생성 
		2) 파일 변경 후 커밋 
		3) 로컬에서 푸쉬(Push) 
		4) 원격에서 Pull request 생성 및 통합(Merge) 
		5) 원격에서 로컬브랜치 삭제

			
3. 되돌리기 ( Undo )
	$ git branch
		 //b1-test-remote
		 //b4-test
		 //* master
    $ git checkout  b4-test
		 //b1-test-remote
		 //	* b4-test
		 //	master

	(01) Revert //직전 커밋 되돌리기
		1) 임의의 파일삭제 ( 'imgs/google.png' )
		2) 커밋  
			$ git status --short //빨강( D   imgs/google.png ) 
			$ git add --all //모든 파일들 스테이징
			$ git status //녹색( D   imgs/google.png )
			$ git commit -a -m "엄청 중요한 파일을 삭제하는 사고를 쳐버렸음"
			$ git status 
		3) 되돌리기 
			$ git revert HEAD --no-edit //되돌리기 성공 
			$ git log --oneline 
				//3fcf3ef (HEAD -> b4-test) Revert "엄청 중요한 파일을 삭제하는 사고를 쳐버렸음"
		4) 파일 복구 확인 ( 'imgs/google.png' )

	(02) Reset //과거의 어느 커밋 되돌리기
		1) 돌아가고 싶은 커밋 번호 찾기 
			$ git log --oneline
			/*
			3fcf3ef (HEAD -> b4-test) Revert "엄청 중요한 파일을 삭제하는 사고를 쳐버렸음"
			b651ed5 엄청 중요한 파일을 삭제하는 사고를 쳐버렸음
			7670396 (origin/b4-test) 로컬브랜치 b4-test에서 커밋 함
			c130d19 (origin/b1-test-remote, b1-test-remote) index.html 수정 by b1-test-remote
			0b81dae index.html에 git.png 적용 업데이트
			3a022e6 깃허브에서의 커밋 메세지
			81a0002 최종 수정 완
			51163b0 commit을 이용해서 b1-test와 병합 완료(Conflict 해결)
			2cc8f57 b1-test 브랜치에서 구글 이미지 추가 했음
			cbf27c1 두번째브랜치(b2-test)에서 추가했음
			08f1606 다음 이미지(daum.png)를 추가하고 index.html에 적용함
			c5684bd index.html에 <p>가 추가되어짐
			2faadc5 첫번째 커밋
			*/

		2) 초기화
			$ git reset --hard 3a022e6 //3a022e6까지는 보존( index.html 수정됨 + imgs/git.png 삭제 확인됨 )
			$ git log --oneline
			/*
			3a022e6 (HEAD -> b4-test) 깃허브에서의 커밋 메세지
			81a0002 최종 수정 완
			51163b0 commit을 이용해서 b1-test와 병합 완료(Conflict 해결)
			2cc8f57 b1-test 브랜치에서 구글 이미지 추가 했음
			cbf27c1 두번째브랜치(b2-test)에서 추가했음
			08f1606 다음 이미지(daum.png)를 추가하고 index.html에 적용함
			c5684bd index.html에 <p>가 추가되어짐
			2faadc5 첫번째 커밋
			*/

		3) Undo Reset //굳이 쓸일이 없을 것 같음 
			$ git reset 3fcf3ef //수정 or 삭제된 파일은 복구 안되네??
			$ git log --oneline
			/*
			3fcf3ef (HEAD -> b4-test) Revert "엄청 중요한 파일을 삭제하는 사고를 쳐버렸음"
			b651ed5 엄청 중요한 파일을 삭제하는 사고를 쳐버렸음
			7670396 (origin/b4-test) 로컬브랜치 b4-test에서 커밋 함
			c130d19 (origin/b1-test-remote, b1-test-remote) index.html 수정 by b1-test-remote
			0b81dae index.html에 git.png 적용 업데이트
			3a022e6 깃허브에서의 커밋 메세지
			81a0002 최종 수정 완
			51163b0 commit을 이용해서 b1-test와 병합 완료(Conflict 해결)
			2cc8f57 b1-test 브랜치에서 구글 이미지 추가 했음
			cbf27c1 두번째브랜치(b2-test)에서 추가했음
			08f1606 다음 이미지(daum.png)를 추가하고 index.html에 적용함
			c5684bd index.html에 <p>가 추가되어짐
			2faadc5 첫번째 커밋
			*/

	(03) Amend //커밋 메세지 덮어씀 ( 최신커밋 -> 수정커밋 )	
		1) 파일 수정
			README.md 수정 ( '마지막 편집내용 추가' )

		2) (오타)커밋
			$ git branch
			//* b4-test
			$ git add README.md
			$ git commit -m "잘못된 커밋 메세지를 커밋함"
			$ git log --oneline

		3) (수정)커밋
			$ git commit --amend -m "잘못된 커밋 메세지를 새로운 메세지로 덮어씀"
			$ git log --oneline
			//fbe49fe (HEAD -> b4-test) 잘못된 커밋 메세지를 새로운 메세지로 덮어씀
			//..


[Tip1] 팔로우 (친구되기)  
	(1) 우측 상단 '고양이아이콘' -> Your profile 
	(2) Edit profile 아래에.. 
		 //4 followers 클릭 
	(3) 각 사용에게 follow 버튼 
		//cf) 친구의 특권 -> 별로없음


[Tip2] 참고URL
	(1) GitHub 공유
	https://subicura.com/git/guide/github.html#github-%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A7%E1%86%A8-%E1%84%8C%E1%85%A5%E1%84%8C%E1%85%A1%E1%86%BC%E1%84%89%E1%85%A9-%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A5%E1%86%BC
	(2) GitHub Flow
	https://subicura.com/git/guide/github-flow.html#github-flow-%E1%84%87%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B5%E1%86%A8

	

			