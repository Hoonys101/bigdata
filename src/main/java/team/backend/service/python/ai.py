import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.tree import plot_tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
# import concurrent.futures

# 인공지능!!!! 어예~!
# 두개의 기업명을 받아서, db로부터 인출하여 normaling 및 라벨링된 dataFrame 산출

# dataFrame을 받아서 라벨링 훈련(3개월로 window로 옮겨가며 훈련)
# 인자를 받아서, normalNlabel을 호출하여 df를 생성하는 것으로 변경
def training(df: pd.DataFrame,model=DecisionTreeClassifier(max_depth=3),default='CLOSE')->list:
#    X, y = prepare_training_data(df)
    result=[]

    # 훈련 데이터와 테스트 데이터로 분할
#    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    train_size = int(0.8 * len(df))
    train_df = df.iloc[:train_size]
    test_df = df.iloc[train_size:]
    
    # 훈련 데이터와 테스트 데이터를 입력 특성과 타겟 값으로 분할
    X_train, y_train = prepare_training_data(train_df)
    X_test, y_test = prepare_training_data(test_df)
    
    # # Decision Tree 모델 생성 및 훈련
    # model = DecisionTreeClassifier(max_depth=3)
#     print(len(X_train))
#     print(X_train.pop().shape)
    train_arrays = [np.array(lst) for lst in X_train]
    X_train_3d=np.stack(train_arrays,axis=0)
    X_train_flatten=X_train_3d.reshape(X_train_3d.shape[0],-1)
    
#    print('X_train:',X_train)
    model.fit(X_train_flatten, y_train)

    # 테스트 데이터로 모델 평가
    test_arrays = [np.array(lst) for lst in X_test]
    X_test_3d=np.stack(test_arrays,axis=0)
    X_test_flatten=X_test_3d.reshape(X_test_3d.shape[0],-1)
    y_pred = model.predict(X_test_flatten)
    accuracy = accuracy_score(y_test, y_pred)
    # print("모델 정확도:", accuracy,flush=True)
    result.append("모델 정확도:"+str(accuracy))
    if(y_pred[-1]!=-1):
        print("현재 주가전파관계가 존재합니다. 확인해보세요. 예측치:",y_pred[-1],"주 전파",flush=True)
        # result.append("현재 주가전파관계가 존재합니다. 확인해보세요. 예측치:"+str(y_pred[-1])+"주 전파")
    else:
        print("아쉽게도 현재 주가 전파관계는 없는 것으로 보이네요.",flush=True)
        # result.append("아쉽게도 현재 주가 전파관계는 없는 것으로 보이네요.")
    # plt.figure(figsize=(20,15))
    # plot_tree(model, filled=True)
    # plt.show()
    return model
    # plot_tree(model,filled=True,feature_names=[])
# dataFrame에서 훈련데이터 준비
def prepare_training_data(df, window_size=60)->tuple[list,list]:
    X = []  # 입력 특성
    y = []  # 타겟 값

    # 윈도우를 이동시켜가며 훈련 데이터 생성
    for i in range(len(df) - window_size):
        window = df.iloc[i:i+window_size]  # 윈도우 설정
        train_X=window.iloc[:-1,:2].to_numpy()
        
        #시간이 지남에 따라 망각 반영
        # for i in range(len(train_X)):
        #     train_X.iloc[i,0]*=(i+1)/59
        
        # 입력 특성: 윈도우의 처음부터 마지막 행의 이전까지
        X.append(train_X)

        # 타겟 값: 윈도우의 마지막 행의 'linkage' 열 값
        y.append(window.iloc[-1]['linkage'])

    return X, y

# 두개의 기업명과 특정 날짜를 입력받고, 예측
# model=DecisionTreeClassifier(max_depth=3)
# #model=training('004020','058430',model)
# training('1152','058430')


# import numpy as np
# import tensorflow as tf
# import gym


# # 시계열 df를 받아서 deep-learning
# def deep(df: pd.DataFrame):

#     # 시계열 주가 데이터 생성 (임의의 데이터로 대체)
#     price_data_1=df.values

#     # LSTM 모델 구축
#     def build_lstm_model(input_shape):
#         model = tf.keras.models.Sequential([
#             tf.keras.layers.LSTM(64, input_shape=input_shape, return_sequences=True),
#             tf.keras.layers.LSTM(64),
#             tf.keras.layers.Dense(1, activation='sigmoid')
#         ])
#         return model

#     # 강화학습 환경 설정
#     class PricePredictionEnv(gym.Env):
#         def __init__(self, price_data_1, window_size):
#             self.price_data_1 = price_data_1
#             # self.price_data_2 = price_data_2
#             self.window_size = window_size
#             self.action_space = gym.spaces.Discrete(2)  # Buy or Hold
#             self.observation_space = gym.spaces.Box(low=0, high=1, shape=(window_size, 1), dtype=np.float32)
#             self.current_index = window_size
        
#         def reset(self):
#             self.current_index = self.window_size
#             return self._get_observation()
        
#         def _get_observation(self):
#             return np.array([self.price_data_1[self.current_index-self.window_size:self.current_index]]).T
        
#         def step(self, action):
#             done = False
#             reward = 0
#             self.current_index += 1
#             if self.current_index >= len(self.price_data_1):
#                 done = True
#             observation = self._get_observation()
#             return observation, reward, done, {}
        
#         def render(self, mode='human'):
#             pass

#     # 모델 학습
#     env = PricePredictionEnv(price_data_1, window_size=60)
#     model = build_lstm_model((env.window_size, 1))
#     model.compile(optimizer=tf.keras.optimizers.Adam(), loss='binary_crossentropy')

#     for episode in range(100):
#         observation = env.reset()
#         done = False
#         while not done:
#             action = np.random.randint(0, 2)
#             next_observation, reward, done, _ = env.step(action)
#             model.train_on_batch(np.expand_dims(observation, axis=0), np.array([action]))
#             observation = next_observation

#     # 모델 평가
#     total_rewards = []
#     for episode in range(10):
#         observation = env.reset()
#         done = False
#         total_reward = 0
#         while not done:
#             action_prob = model.predict(np.expand_dims(observation, axis=0))[0][0]
#             action = 1 if action_prob >= 0.5 else 0
#             next_observation, reward, done, _ = env.step(action)
#             total_reward += reward
#             observation = next_observation
#         total_rewards.append(total_reward)

#     average_reward = np.mean(total_rewards)
#     print("Average reward:", average_reward)